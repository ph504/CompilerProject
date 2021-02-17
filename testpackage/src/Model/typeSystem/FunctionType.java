package Model.typeSystem;

import java.util.ArrayList;
import java.util.List;

public class FunctionType extends DSCP{

    public String funcID;
    private List<DSCP> argsType = new ArrayList<>();
    private DSCP returnType;


    public FunctionType(DSCP rtype, List<DSCP> args){
        this();
        argsType = args;
        this.returnType = rtype;
    }

    /*FunctionType(List<DSCP> args){
        this();
        argsType = args;
    }*/

    public FunctionType(DSCP rtype){
        this();
        this.returnType = rtype;
    }

    @Override
    public void setArgs(List<DSCP> argsType) {
        this.argsType = argsType;
    }

    private FunctionType(){
        super("functionType");
    }

    @Override
    public boolean isConvertible(DSCP dt) {
        return returnType.isConvertible(dt);
    }

    @Override
    public boolean isTypeof(DSCP dt) {
        return type.equals(dt.type);
    }

    @Override
    public DSCP createType() {
        return returnType.createType();
    } // this is correct I KNOW it.

    public List<DSCP> getArgsType() {
        return argsType;
    }

    @Override
    public DSCP opAssignment(DSCP other) {
        throw new SemanticErrorException("");
    }

    public DSCP getReturnType() {
        return returnType;
    }

    @Override
    public DSCP opCall(List<DSCP> args) {
        int aargNum = argsType.size();
        int dargNum = args.size();
        // the number of arguments should be equal.
        if(aargNum==dargNum) {
            for (int i = 0; i < aargNum; i++) {
                if (!args.get(i).isConvertible(argsType.get(i)))
                    throw new SemanticErrorException("");
            }
            /*generate code here?*/
            return returnType;
        }
        else
            throw new SemanticErrorException("");
    }
}
