package Model.typeSystem;


import Model.CodeGen;

public class BoolType extends DSCP {

    public BoolType() {
        super("bool");
    }

    @Override
    public DSCP opAssignment(DSCP other) {

        CodeGen.intAssignmentCgen(other);
        CodeGen.pushMips(3);

        return this;
    }

    @Override
    public boolean isConvertible(DSCP dt) {
        return type.equals(dt.type);
    }

    @Override
    public DSCP createType() {
        return this;
    }

    @Override
    public DSCP opBoolean(DSCP other, String opb) {

        if (!opb.equals("!")) {

            CodeGen.popMips(2);
        }
        CodeGen.popMips(1);

        CodeGen.booleanOprations(opb);
        CodeGen.pushMips(3);

        return this;
    }

    @Override
    public DSCP opBtoI() {
        return new IntType();
    }

    @Override
    public DSCP stPrint() {

        // CodeGen.BooleanPrint();
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
