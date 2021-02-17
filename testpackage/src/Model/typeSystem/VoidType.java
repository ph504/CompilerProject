package Model.typeSystem;

public class VoidType extends DSCP{

    public VoidType(){
        super("void");
    }
    @Override
    public boolean isConvertible(DSCP dt) {
        return type.equals(dt.type);
    }
}
