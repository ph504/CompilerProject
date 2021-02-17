package Model.typeSystem;

public class NullType extends DSCP{

    NullType(){
        super ("null");
    }

    @Override
    public boolean isConvertible(DSCP dt) { // null type is convertible to all class types.
        return type.equals(dt.type) || dt instanceof ClassType || dt instanceof InterfaceType; // or dt instanceof ClassType.
    }

    @Override
    public DSCP createType() {
        return this;
    }

    @Override
    public DSCP pushStack() {

        return this;
    }
}
