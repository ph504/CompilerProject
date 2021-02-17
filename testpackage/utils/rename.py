#!/usr/bin/env python3

"""
Powerful file rename tool

Usage:
    rename                [options] (<source_pattern> | <source_pattern> <destination_pattern>)

Arguments:
    source_pattern        the pattern of input files
    destination_pattern   the pattern to which rename files to

Options:
    --list, -L                          only show matching files (don't rename, don't use second pattern; implies dry mode).
    --dry, -d                           do not rename files, just show the changes.
    --verbose, -v                       be more verbose on everything.
    --quiet, -q                         be more hesitant in printing.
    --long, -l                          long mode.
    --pretty, -p                        use pretty fixed width formatting for easier readability.
    --count, -c N                       only rename N files.
    --tty, -t (auto|on|off)             whether to use ansi color codes [default: auto].
    --help, -h                          show this message.
"""

import sys
import os, os.path

from lark import Lark, Transformer


class RangeSpec:
    class Iter:
        i = None
        format_spec = None
        end = None

        def __init__(self, parent):
            self.parent = parent
            self.format_spec = "{:0" + str(parent.size) + "d}" if parent.size is not None else "{:d}"
            if parent.end is not None:
                self.end = parent.end
            if parent.count is not None:
                self.end = parent.count
            self.reset()

        def reset(self):
            self.i = self.parent.start

        def __iter__(self):
            return self

        def __next__(self):
            if self.end is not None and self.i > self.end:
                self.reset()
                if self.parent.stopping:
                    raise StopIteration()

            s = self.format_spec.format(self.i)
            self.i += self.parent.step
            return s

    count = None

    def __init__(self, start=None, size=None, end=None, step=None, stopping=None, depth=None, auto=None):
        if start is None:
            start = 0
        if step is None:
            step = 1
        if stopping is None:
            stopping = True
        if depth is None:
            depth = 0
        if auto is None:
            auto = False

        self.start = start
        self.size = size
        self.end = end
        assert step > 0
        self.step = step
        self.stopping = stopping
        self.depth = depth
        self.auto = auto

    def __iter__(self):
        return self.Iter(self)

    def set_count(self, n):
        self.count = n


class GlobPattern:
    """
    Read a pattern as a string, and parses parts according to this legend:

    [] # specifies a range spec.
    [(2)] # sets a depth marker for spec (depth is 0 if missing).
    [##] # determine size of glob pattern (like 01).
    [1] # set the starting number.
    [-10] # set the max number (used for looping and automatic glob size).
    [,2] # desired step number (default: 1).
    [/] # as the last character, automatically determine the glob size.
    [n] # set spec as non-stopping.
    [s] # explicitly set spec as stopping.

    some examples:
    [###10-20,1/]
    [#0,9n]
    [(2)1,2/]

    glob size priority:
    1. <size specifier> -> ##
    2. if auto is set: <runtime length argument> -> when calling the function
    3. if auto is set: <end>
    4. no size (i.e. 1,2,...,10,11,...100)
    """

    # TODO: add \ as an escape to use [ and ] in pattern string

    grammar = r'''
    
    pattern : (range_spec | CHAR)*
    
    range_spec : "[" [depth_spec] [size_spec] [start_spec] [end_spec] [step_spec] [auto_spec] [stopping_spec] "]"
    
    depth_spec : "(" NUMBER ")"
    
    size_spec : /#+/
    
    start_spec : NUMBER
    
    end_spec : "-" NUMBER
    
    step_spec : "," NUMBER
    
    auto_spec : "/"
    
    stopping_spec : "s" -> is_stopping
                  | "n" -> is_non_stopping
    
    CHAR : /[^\[]+/
    
    %import common.INT -> NUMBER
    
    '''

    class TreeParser(Transformer):
        depth = None
        size = None
        start = None
        end = None
        step = None
        auto = None
        stopping = None

        def reset(self):
            self.depth = None
            self.size = None
            self.start = None
            self.end = None
            self.step = None
            self.auto = None
            self.stopping = None

        def pattern(self, items):
            specs = []
            s = ''
            for i in items:
                if type(i) is RangeSpec:
                    s += '{}'
                    specs.append(i)
                else:
                    s += i

            return s, specs

        def range_spec(self, items):
            rs = RangeSpec(start=self.start, size=self.size, end=self.end, auto=self.auto,
                           step=self.step, depth=self.depth, stopping=self.stopping)
            self.reset()
            return rs

        def depth_spec(self, items):
            self.depth = int(items[0])

        def size_spec(self, items):
            self.size = len(items[0])

        def start_spec(self, items):
            self.start = int(items[0])

        def end_spec(self, items):
            self.end = int(items[0])

        def step_spec(self, items):
            self.step = int(items[0])

        def auto_spec(self, items):
            self.auto = True

        def is_stopping(self, items):
            self.stopping = True

        def is_non_stopping(self, items):
            self.stopping = False

    def __init__(self, pattern):
        parser = Lark(self.grammar, start='pattern')
        tree = parser.parse(pattern)
        # print(tree.pretty())
        self.tmpl, self.specs = self.TreeParser().transform(tree)
        self.pattern = pattern

    class Iter:
        current_level = 1
        iters = None
        values = None
        max_level = None
        level_up = None
        should_stop = False
        go_back = False

        def __init__(self, tmpl, specs, spec_counts):
            self.tmpl = tmpl

            self.levels = {}
            self.iters = {}
            self.values = {}
            self.max_level = -1

            current_pos = 0
            for spec in specs:

                if current_pos in spec_counts:
                    spec.set_count(spec_counts[current_pos])

                l: int = spec.depth
                if l not in self.levels:
                    self.levels[l] = []
                    self.iters[l] = []

                self.levels[l].append(spec)
                # TODO: only first level iters are really needed.
                self.iters[l].append((current_pos, iter(spec)))

                self.max_level = max(self.max_level, l)
                current_pos += 1

            self.current_level = 1 if self.max_level >= 1 else 0
            self.level_up = True

            for i in range(0, self.max_level):
                if i not in self.iters:
                    self.iters[i] = []
                    self.levels[i] = []

        def __iter__(self):
            return self

        def __next__(self):

            if self.should_stop:
                raise StopIteration()

            # check for finish
            if self.current_level == self.max_level + 1 or self.max_level == 0:

                # need to do all level 0 iters every time.
                if 0 in self.iters:
                    for cpos0, citer0 in self.iters[0]:
                        nx = next(citer0)
                        self.values[cpos0] = nx

                self._stop_level()
                # end result
                return self.render()

            level_iters = self.iters[self.current_level]
            level_max = len(level_iters)

            cc = 0
            for cpos, citer in level_iters:
                cc += 1

                try:
                    nx = next(citer)
                except StopIteration:
                    if self.go_back:
                        self._stop_level()
                        self.go_back = False
                    self._stop_level()
                    return self.__next__()

                self.values[cpos] = nx
                if cc == level_max:
                    if self.go_back:
                        self._stop_level()
                        self.go_back = False
                    else:
                        self._next_level()
                    return self.__next__()

            # an empty level
            if cc == 0:
                if self.level_up:
                    self._next_level()
                else:
                    self._stop_level()

            return self.__next__()

        def _next_level(self):
            self.current_level += 1
            if self.current_level == self.max_level + 1:
                return

            t = 0
            iters = []
            for pos, _ in self.iters[self.current_level]:
                iters.append((pos, iter(self.levels[self.current_level][t])))
                t += 1

            self.iters[self.current_level] = iters
            self.level_up = True

        def _stop_level(self):
            if self.current_level == 1 or self.should_stop:
                raise StopIteration()
            self.current_level -= 1
            if self.current_level < 0:
                self.current_level = 0
            if self.current_level != 1:
                self.level_up = False
            else:
                self.level_up = True

        def stop_level(self):
            self.go_back = True

        def render(self):
            return self.tmpl.format(*[self.values[i] for i in sorted(self.values)])

    def __iter__(self):
        return self.glob()

    def glob(self, spec_counts=None):
        if spec_counts is None:
            spec_counts = {}

        return self.Iter(self.tmpl, self.specs, spec_counts)


# gp = GlobPattern(r't[##1]-[(1)1]-expr[(2)1].in')
# it = gp.glob({1: 3, 2: 4})
# c = 0
# # FIXME: when in between states
# while True:
#     c += 1
#     try:
#         print(next(it))
#     except StopIteration:
#         break
#     if c == 4:
#         # pass
#         it.stop_level()

from docopt import docopt

"""if __name__ == "__main__":
    arguments = docopt(__doc__)

    tty = arguments['--tty']
    if tty not in ['auto', 'on', 'off']:
        print('Option --tty must be auto | on | off. given {}.'.format(tty))
        exit(1)
    if tty == 'auto':
        tty = sys.stdout.isatty()
    elif tty == 'on':
        tty = True
    elif tty == 'off':
        tty = False

    verbosity = 'verbose' if arguments['--verbose'] else 'normal' if not arguments['--quiet'] else 'quiet'

    options = {'source_pattern': arguments['<source_pattern>'] if arguments['<source_pattern'] is not None else None,
               'destination_pattern': arguments['<destination_pattern>'] if arguments['<destination_pattern'] is not None else None,
               'N': int(arguments['--count']) if arguments['--count'] is not None else None}"""

# TODO: stopping should be False for level 0


## AREA 51

# sp = GlobPattern("lexer-sv/t[(1)1-11s]-*[(2)1].in")
# dp = GlobPattern("lexer-sv/t[##1]-%[(2)1].in")
sp = GlobPattern("expr/t[###1-65s]-*[(1)1n].in")
dp = GlobPattern("expr/t[###1].in")

sit = sp.glob()
dit = dp.glob()

import re
import glob

c = 0

while True:
    try:
        sfglob: str = next(sit)
    except StopIteration:
        break

    file_matches = glob.glob(sfglob)
    if len(file_matches) == 0:
        sit.stop_level()
        dit.stop_level()
        continue
    elif len(file_matches) > 1:
        # mn = min(map(len, file_matches))
        # file_match_l = list(filter(lambda fname: len(fname) == mn, file_matches))
        # if len(file_match_l) != 1:
        #     raise Exception("WTH")
        # file_match = file_match_l[0]
        for s in file_matches:
            if re.match(sfglob.replace("*", "([a-zA-Z])*"), s):
                file_match = s
                break
    else:
        file_match = file_matches[0]

    c += 1
    dfglob: str = next(dit)

    star_index = sfglob.find("*")
    rest = sfglob[star_index + 1:]
    rest_index = file_match.find(rest)

    dest_name = dfglob.replace("%", file_match[star_index:rest_index])
    print(file_match, dest_name)

    os.rename(file_match, dest_name)
