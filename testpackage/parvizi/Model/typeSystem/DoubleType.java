package Model.typeSystem;

import Model.CodeGen;

public class DoubleType extends DSCP {
    public DoubleType(){super("double");}

    @Override
    public boolean isConvertible(DSCP dt) {
        return type.equals(dt.type);
    }

    @Override
    public DSCP createType() {
        return this;
    }

    //public DoubleType(String value){ super("double",value);}

    public DSCP opPlus(DSCP operand2) {
/*double newVal = Double.parseDouble(operand2.getValue()) + Double.parseDouble(value);
return new DoubleType(String.valueOf(newVal));*/
        CodeGen.popMipsDouble(0);
        CodeGen.popMipsDouble(2);
        CodeGen.PlusDoubleGen();
        CodeGen.pushMipsDouble(4);

        return this;
    }

    @Override
    public DSCP opMinus(DSCP operand2) {
/*double newVal = Double.parseDouble(operand2.getValue()) - Double.parseDouble(value);
return new DoubleType(String.valueOf(newVal));*/
        CodeGen.popMipsDouble(2);
        CodeGen.popMipsDouble(0);
        CodeGen.MinusDoubleGen();
        CodeGen.pushMipsDouble(4);

        return this;
    }

    @Override
    public DSCP opDiv(DSCP operand2) {
/*double newVal = Double.parseDouble(operand2.getValue()) / Double.parseDouble(value);
return new DoubleType(String.valueOf(newVal));*/
        CodeGen.popMipsDouble(2);
        CodeGen.popMipsDouble(0);
        CodeGen.DivDoubleGen();
        CodeGen.pushMipsDouble(4);

        return this;
    }

    @Override
    public DSCP opMult(DSCP operand2) {

        CodeGen.popMipsDouble(2);
        CodeGen.popMipsDouble(0);
        CodeGen.MultDoubleGen();
        CodeGen.pushMipsDouble(4);

        return this;
    }

    @Override
    public DSCP opMod(DSCP other) {

        CodeGen.popMipsDouble(2);
        CodeGen.popMipsDouble(0);
        CodeGen.ModDoubleGen();
        CodeGen.pushMipsDouble(4);

        return this;
    }

    @Override
    public DSCP opNegate() {

        CodeGen.popMipsDouble(0);
        CodeGen.UMinusDoubleGen();
        CodeGen.pushMipsDouble(4);
        return this;
    }

    @Override
    public DSCP opDtoI() {
        CodeGen.popMipsDouble(0);
        CodeGen.dtoiGen();
        CodeGen.pushMips(3);
        return new IntType();
    }

    @Override
    public DSCP opAssignment(DSCP other) {

        CodeGen.popMipsDouble(4);
        CodeGen.doubleAssignmentCgen(other);
        CodeGen.pushMipsDouble(6);

        return this;
    }

    @Override
    public DSCP opfetchRV() {

        CodeGen.fetchRValDouble(this);

        return this;
    }

    @Override
    public DSCP opComparator(DSCP other, String opcomp) {

        CodeGen.popMipsDouble(2);
        CodeGen.popMipsDouble(0);
        CodeGen.doubleBooleanOprationsCgen(opcomp);
        CodeGen.pushMips(3);

        return new BoolType();
    }

    @Override
    public DSCP varDecl(String id) {
        CodeGen.declDoubleCgen(id);
        return this;
    }

    @Override
    public DSCP pushStack() {
        CodeGen.pushMipsDouble(4);
        return this;
    }

    @Override
    public DSCP stPrint() {

        CodeGen.printDouble();
        return this;
    }

}
