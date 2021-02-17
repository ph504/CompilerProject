package Model.typeSystem;

public class InterfaceType extends DSCP{
    /*String interfaceName;*/
    public InterfaceType(String interfaceName){
        super(interfaceName);
        /*this.interfaceName = interfaceName;*/
    }

    @Override
    public boolean isConvertible(DSCP dt) {
        return type.equals(dt.type);
    }

    @Override
    public DSCP idType(String id) {

        return new InterfaceType(id);
    }
}
