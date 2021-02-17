import sys, getopt

from lark import Lark, tree
import lark.exceptions

decaf_parser = Lark.open('expr.lark', start='exprs', parser='lalr', lexer='standard', debug=False)


def main(argv):
    inputfile = ''
    outputfile = ''
    try:
        opts, args = getopt.getopt(argv, "hi:o:", ["ifile=", "ofile="])
    except getopt.GetoptError:
        print('main.py -i <inputfile> -o <outputfile>')
        sys.exit(2)
    for opt, arg in opts:
        if opt == '-h':
            print('test.py -i <inputfile> -o <outputfile>')
            sys.exit()
        elif opt in ("-i", "--ifile"):
            inputfile = arg
        elif opt in ("-o", "--ofile"):
            outputfile = arg

    with open("tests/" + inputfile, "r") as input_file:
        str = input_file.read()
        exception = False
        try:
            t = decaf_parser.parse(str)
        except lark.exceptions.ParseError as e:
            exception = True
            exception_obj = e

    with open("out/" + outputfile, "w") as output_file:
        # write result to output file.
        # for the sake of testing :
        if not exception:
            print(t.pretty(), file=output_file, end="")
            # print("YES", file=output_file)
            # tree.pydot__tree_to_png(t, "out/" + outputfile + ".png")
        else:
            # print("NO", file=output_file)
            print(exception_obj, file=output_file)


if __name__ == "__main__":
    main(sys.argv[1:])
