package Model.typeSystem;



import Model.DecafAccessMode;

import java.util.List;

public abstract class DSCP {


    String address;
    String type;
    DecafAccessMode accessMode;
    public String value;


    public DSCP(String type) {
        this.type = type;
        accessMode = DecafAccessMode.DEFAULT;
    }

    //DSCP(){}

    public DecafAccessMode getAccessMode() {
        return accessMode;
    }

    public void setArgs(List<DSCP> argsType){}

    public void setAccessMode(DecafAccessMode accessMode) {
        this.accessMode = accessMode;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public abstract boolean isConvertible(DSCP dt); // compares the this type datatype with the argument given datatype.

    public boolean isTypeof(DSCP dt){return type.equals(dt.type);}

    //public int getSize()  {return 0;}                // gets the size of the datatype dedicated for the memory. for example if it is a class how much memory it needs?

    //public abstract void setValue(String value);

    public void addMember(DSCP ft){
        /*Error Type*/
    } // no implementation for ClassType ONLY

    public DSCP createType(){return null;}

    //public abstract DSCP createType(String value);

    public DSCP opNew(){
        throw new SemanticErrorException("");
    } // no implementation for ClassType ONLY

    public DSCP opPlus(DSCP other){
        throw new SemanticErrorException("");
    } // done.

    public DSCP opMinus(DSCP other){
        throw new SemanticErrorException("");
    } // done.

    public DSCP opDiv(DSCP other){
        throw new SemanticErrorException("");
    } // done.

    public DSCP opMult(DSCP other){
        throw new SemanticErrorException("");
    } // done.

    public DSCP opMod(DSCP other){
        throw new SemanticErrorException("");
    } // some questions/doubts here.

    public DSCP opNegate(){
        throw new SemanticErrorException("");
    } // done.

    public DSCP opComparator(DSCP other, String opcomp){
        throw new SemanticErrorException("");
    } // some questions/doubts here.

    public DSCP opBoolean(DSCP other, String opb){
        throw new SemanticErrorException("");
    } // done.

    public DSCP opAssignment(DSCP other){ // other is the assigning variable and this is the assigned variable // this = other;
        return this;
    } // idk if it works.

    public DSCP opItoD(){

        throw new SemanticErrorException("");
    }

    public DSCP opBtoI(){
        throw new SemanticErrorException("");
    }

    public DSCP opItoB(){
        throw new SemanticErrorException("");
    }

    public DSCP opDtoI(){
        throw new SemanticErrorException("");
    }

    public DSCP opfetchRV(){
        throw new SemanticErrorException("");
    }

    public DSCP stPrint() {
        throw new SemanticErrorException("");
    }

    public DSCP opCall(List<DSCP> args){
        throw new SemanticErrorException("");
    }

    public DSCP varDecl(String id){
        throw new SemanticErrorException("");
    }

    public DSCP opBrackets(){
        throw new SemanticErrorException("");
    }

    /*protected int getValue() {
        return 0; // dummy and no use.
    }*/

    public DSCP indexWrite(DSCP other){ // this = other;
        /*t6 -> index,
        * CodeGen.writeArray(this)
        * arg.getAddress()*/

        return this;
    }

    public DSCP pushStack(){return this; /*shouldn't actually be called in this case.*/ }

    public DSCP idType(String id){
        throw new SemanticErrorException("");
    }

    public void opExtend(ClassType child){
        throw new SemanticErrorException("");
    }
}






