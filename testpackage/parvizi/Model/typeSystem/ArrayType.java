package Model.typeSystem;

public class ArrayType extends DSCP{
    DSCP elementType;

    public ArrayType(){
        super("arrayType");
    }
    public ArrayType(DSCP elementType){
        super("arrayType");
        this.elementType = elementType;
    }

    @Override
    public boolean isConvertible(DSCP dt) {
        if(type.equals(dt.type)) {
            ArrayType other = (ArrayType) dt;
            return elementType.isConvertible(other.elementType);
        }
        else
            return false;
    }

    @Override
    public DSCP createType() {
        return elementType;
    } // to be discussed more in the future.

    @Override
    public DSCP opBrackets() {
        /*put codegen here.*/
        return elementType.pushStack();
    }

    @Override
    public DSCP pushStack() {

        return this;
    }

    @Override
    public DSCP varDecl(String id) {
        return this;
    }

    @Override
    public DSCP opfetchRV() {
        return this;
    }
}
