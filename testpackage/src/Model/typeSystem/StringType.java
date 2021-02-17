package Model.typeSystem;


import Model.CodeGen;

public class StringType extends DSCP {
    public StringType(){super("string");}

    @Override
    public boolean isConvertible(DSCP dt) {
        return type.equals(dt.type);
    }

    @Override
    public DSCP createType() {
        return this;
    }

    @Override
    public DSCP opPlus(DSCP operand2) {

        CodeGen.stringConcatCgen();

        return this;
    }

    @Override
    public DSCP opAssignment(DSCP other) {

        CodeGen.popMips(3);   // maybe not needed.
        CodeGen.stringAssignment(other);
        //CodeGen.pushMips(3);

        return this;
    }

    @Override
    public DSCP opComparator(DSCP operand2, String opcomp) {
        switch (opcomp){
            case "==":

                CodeGen.CheckEqualCgen();
                CodeGen.pushMips(3);

                return new BoolType();

            case "!=":

                CodeGen.CheckNotEqualCgen();
                CodeGen.pushMips(3);

                return new BoolType();

            default:
                throw new SemanticErrorException("");

        }
    }

    @Override
    public DSCP stPrint() {

        CodeGen.printString();
        return this;
    }

    @Override
    public DSCP opfetchRV() {

        CodeGen.fetchRVal(this);
        return this;
    }

    @Override
    public DSCP varDecl(String id) {

        CodeGen.declStringCgen(id);
        return this;
    }

    @Override
    public DSCP pushStack() {

        return this;
    }
}
