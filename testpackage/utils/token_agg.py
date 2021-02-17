import sys, getopt

from lark import Lark
import lark.exceptions

decaf_parser = Lark.open('decaf.lark', start='program', parser='earley', lexer='standard', debug=False)


token_set = {"IDENTIFIER",
             "INT_CONSTANT",
             "DOUBLE_CONSTANT",
             "STRING_CONSTANT"}


def main(argv):

    all_tokens = set()
    import glob
    for inputfile in glob.glob("tests/parser/corrects/0-*.in"):
        with open(inputfile, "r") as input_file:
            s = input_file.read()
            tokens = decaf_parser.lex(s)
            all_tokens.update([t.type if t.type in token_set else str(t) for t in tokens])

    with open("out/tokens_of_0tests.out", "w") as output_file:
        # write result to output file.
        for t in all_tokens:
            print(t, file=output_file)

    with open("out/all_tokens.out", "r") as tokens_file:
        all_tokens_overall = set([line.strip() for line in tokens_file.readlines()])
        print("In the list but not in the tests:")
        for t in all_tokens_overall.difference(all_tokens):
            print(t)

        print("\nIn tests but not in the list:")
        for t in all_tokens.difference(all_tokens_overall):
            print(t)


if __name__ == "__main__":
    main(sys.argv[1:])
