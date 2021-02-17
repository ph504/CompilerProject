package Model.typeSystem;


import Model.CodeGen;

public class IntType extends DSCP {


    public IntType() {
        super("int");
    }

    @Override
    public boolean isConvertible(DSCP dt) {
        return dt instanceof IntType;
    }

    @Override
    public DSCP createType() {
        return this;
    }

    /*-------------------------operators-------------------------*/
    @Override
    public DSCP opPlus(DSCP other) {

        CodeGen.popMips(1);
        CodeGen.popMips(2);
        CodeGen.PlusIntGen();
        CodeGen.pushMips(3);

        return this;
    }

    @Override
    public DSCP opMinus(DSCP other) {

        CodeGen.popMips(2);
        CodeGen.popMips(1);
        CodeGen.MinusIntGen();
        CodeGen.pushMips(3);

        return this;
    }

    @Override
    public DSCP opAssignment(DSCP other) {

        CodeGen.intAssignmentCgen(other);
        CodeGen.pushMips(3);

        return this;
    }

    @Override
    public DSCP opMod(DSCP other) {

        CodeGen.popMips(2);
        CodeGen.popMips(1);
        CodeGen.ModIntGen();
        CodeGen.pushMips(3);

        return this;
    }

    @Override
    public DSCP opDiv(DSCP other) {

        CodeGen.popMips(2);
        CodeGen.popMips(1);
        CodeGen.DivIntGen();
        CodeGen.pushMips(3);

        return this;
    }

    @Override
    public DSCP opMult(DSCP other) {

        CodeGen.popMips(2);
        CodeGen.popMips(1);
        CodeGen.multIntGen();
        CodeGen.pushMips(3);

        return this;
    }

    @Override
    public DSCP opItoB() {

        CodeGen.popMips(1);
        CodeGen.itobCgen();

        return new BoolType();
    }

    @Override
    public DSCP opItoD() {

        CodeGen.popMips(0);
        CodeGen.itodGen();
        CodeGen.pushMipsDouble(4);
        return new DoubleType();
    }

    @Override
    public DSCP opNegate() {

        CodeGen.popMips(1);
        CodeGen.uMinusIntGen();
        CodeGen.pushMips(3);

        return this;
    }

    @Override
    public DSCP opComparator(DSCP other, String opcomp) {

        CodeGen.popMips(2);
        CodeGen.popMips(1);
        CodeGen.intBooleanOprationsCgen(opcomp);
        CodeGen.pushMips(3);

        return new BoolType();
    }

    /*-------------------------operators-------------------------*/

    @Override
    public DSCP stPrint() {

        CodeGen.printInt();
        return this;
    }

    @Override
    public DSCP opfetchRV() {

        CodeGen.fetchRVal(this);
        return this;
    }

    @Override
    public DSCP varDecl(String id) {
        CodeGen.declIntCgen(id);
        return this;
    }

    @Override
    public DSCP pushStack() {
        CodeGen.pushMips(3);
        return this;
    }
}

