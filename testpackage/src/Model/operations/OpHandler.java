package Model.operations;



import Model.CodeGen;
import Model.DecafAccessMode;
import Model.multipass.IPass;
import Model.scope.STSStack;
import Model.typeSystem.*;


import java.util.List;

public class OpHandler {

    /*-------------------------operations-------------------------*/
    // implemented for types: Int, Double, String
    public static DSCP add(DSCP o1, DSCP o2){
        if(o1.isConvertible(o2)){
            return o2.opPlus(o1);
        }
        else if(o2.isConvertible(o1)){
            return o1.opPlus(o2);
        }
        else
            throw new SemanticErrorException("");
    }

    // implemented for types: Int, Double
    public static DSCP subtract(DSCP o1, DSCP o2){
        if(o1.isConvertible(o2)){
            return o2.opMinus(o1);
        }
        else if(o2.isConvertible(o1)){
            return o1.opMinus(o2);
        }
        else
            throw new SemanticErrorException("");
    }

    // implemented for types: Int, Double
    public static DSCP divide(DSCP o1, DSCP o2){
        if(o1.isConvertible(o2)){
            return o2.opDiv(o1);
        }
        else if(o2.isConvertible(o1)){
            return o1.opDiv(o2);
        }
        else
            throw new SemanticErrorException("");
    }

    // implemented for types: Int, Double
    public static DSCP multiply(DSCP o1, DSCP o2){
        if(o1.isConvertible(o2)){
            return o2.opMult(o1);
        }
        else if(o2.isConvertible(o1)){
            return o1.opMult(o2);
        }
        else
            throw new SemanticErrorException("");
    }

//    public DSCP declVar(DSCP type){
//
//    }

    // implemented for types: Int
    public static DSCP mod(DSCP o1, DSCP o2){
        if(o1.isConvertible(o2)){
            return o2.opMod(o1);
        }
        else if(o2.isConvertible(o1)){
            return o1.opMod(o2);
        }
        else
            throw new SemanticErrorException("");
    }

    // implemented for types : Double, Int
    // might come with error. check it later in test.
    public static DSCP negate(DSCP o1){
        return o1.opNegate();
    }

    // implemented for types : Class
    public static DSCP instantiate(DSCP o1){
        return o1.opNew();
    }

    // implemented for types : Double, Int, String
    public static DSCP compare(DSCP o1, DSCP o2, String opcomp){ // o1 !<==>? o2


        if(o1.isConvertible(o2)){ // o1 is convertible to o2
            return o2.opComparator(o1,opcomp);
        }
        else if(o2.isConvertible(o1)){ // o2 is convertible to o1.
            // which means the o1 was not convertible to o2 so it doesn't matter if the program executes logically correct.
            return o1.opComparator(o2,opcomp);
        }
        else
            throw new SemanticErrorException("");
    }

    // implemented for types : Bool
    public static DSCP opBool(DSCP o1, DSCP o2, String opb){
        if(o1.isConvertible(o2)){
            return o2.opBoolean(o1,opb);
        }
        else if(o2.isConvertible(o1)){
            // which means the o1 was not convertible to o2 so it doesn't matter if the program executes logically correct.
            return o1.opBoolean(o2,opb);
        }
        else
            throw new SemanticErrorException("");
    }

    public static DSCP assign(DSCP o1, DSCP o2){ // o2 = o1;

        if(o1.isConvertible(o2))
            if(isArr)
                return o2.indexWrite(o1);
            else
                return o2.opAssignment(o1);
        else
            throw new SemanticErrorException("");


    }


    public static DSCP btoi(DSCP o1){
        if(o1.isConvertible(new BoolType()))
            return o1.opBtoI();
        else
            throw new SemanticErrorException("");
    }

    public static DSCP itob(DSCP o1){
        if(o1.isConvertible(new IntType()))
            return o1.opItoB();
        else
            throw new SemanticErrorException("");
    }

    public static DSCP dtoi(DSCP o1){
        if(o1.isConvertible(new DoubleType()))
            return o1.opDtoI();
        else
            throw new SemanticErrorException("");
    }

    public static DSCP itod(DSCP o1){
        if(o1.isConvertible(new IntType()))
            return o1.opItoD();
        else
            throw new SemanticErrorException("");
    }

    public static DSCP readInteger(){
        CodeGen.readInt();
        return new IntType();
    }

    public static DSCP readLine(){
        CodeGen.readLine();
        return new StringType();
    }

    public static DSCP arrayIndexingR(DSCP array, DSCP index){
        if (!index.isConvertible(new IntType())) {
            throw new SemanticErrorException("");
        }
        /*codegen here. pop the index and read from array in heap and push to stack the result*/
        return array.opBrackets();

    }

    public static DSCP arrayIndexingL(DSCP array, DSCP index){
        if (!index.isConvertible(new IntType())) {
            throw new SemanticErrorException("");
        }
        /*setAddress + t6*/
        return array.opBrackets();

    }

    public static DSCP checkReturnType(DSCP ret){
        FunctionType myFunc = STSStack.stack.searchFunctionType();
        if(myFunc == null)
            throw new SemanticErrorException("");
        DSCP myRet = myFunc.getReturnType();
        if(ret.isConvertible(myRet)) {
            /*put cgen here.*/
            return myRet;
        }
        else
            throw new SemanticErrorException("");
    }

    public static DSCP callFunction(String funcID, List<DSCP> args){

        DSCP stid = STSStack.stack.getDSCP(funcID); // stid stands for symbol table id.

        return stid.opCall(args);
    }

    public static DSCP fetchRvalue(String id){
        DSCP variable = STSStack.stack.getDSCP(id);
        return variable.opfetchRV();
    }

    /*-------------------------operations-------------------------*/

    /*-------------------------statements-------------------------*/
    public static void printLine(/*List<DSCP> expressions*/){
        /*for (int i = expressions.size(); i > 0; i--) {
            expressions.get(i).stPrint();
        }*/
        //CodeGen.printNewLine();
        CodeGen.printNewLineCgen();
    }

    public static void condition(DSCP expr){
        if(!expr.isConvertible(new BoolType()))
            throw new SemanticErrorException("");
    }

    public static DSCP funcDecl(String id, DSCP retType){
        if(IPass.pass.equals("second"))
            if(STSStack.stack.searchID(id)) // the id has already been used.
                throw new SemanticErrorException("");
        FunctionType func = new FunctionType(retType);
        STSStack.stack.addToST(id, func); // add the function to the symbol table.
        /*put label function here by codegen.*/
        return func;
    }

    public static void giveArg(String funcID, List<DSCP> args){
        STSStack.stack.getDSCP(funcID).setArgs(args);
        /*CodeGen.createArguments() here */
    }

    public static DecafAccessMode createAccessMode(String am){
        return DecafAccessMode.valueOf(am.toUpperCase());
    }

    public static void breakSt() {
        if(STSStack.stack.searchLoop())
            CodeGen.breakCgen();
        else
            throw new SemanticErrorException("");
    }

    public static void contSt() {
        if (STSStack.stack.searchLoop())
            CodeGen.continueCgen();
        else
            throw new SemanticErrorException("");
    }
    /*-------------------------statements-------------------------*/

    public static boolean isPrintSt = false;
    public static boolean isArg = false;
    public static DSCP exprComma(DSCP expr){
        if(isPrintSt)
            return expr.stPrint();
        else if(isArg)
            return expr;
        else
            throw new SemanticErrorException("");
    }

    public static DSCP varDecl(DSCP type, String id){
        if(STSStack.stack.searchID(id))
            throw new SemanticErrorException("");
        STSStack.stack.addToST(id, type); // the id is added to the symbol table.
        return type.varDecl(id);
    }

    public static DSCP newArray(DSCP expr, DSCP type){
        if(expr.isConvertible(new IntType())){
            return new ArrayType(type);
        }
        else
            throw new SemanticErrorException("");

    }

    public static boolean isArr = false;

    public static DSCP classDecl(String classID){
        if(STSStack.stack.searchID(classID))
            throw new SemanticErrorException("");
        ClassType c = new ClassType(classID);
        STSStack.stack.addToST(classID,c);
        STSStack.stack.newClassScope(classID);
        return c;
    }

    public static DSCP interfaceDecl(String interfaceID){
        if(STSStack.stack.searchID(interfaceID))
            throw new SemanticErrorException("");
        InterfaceType i = new InterfaceType(interfaceID);
        STSStack.stack.addToST(interfaceID,i);
        STSStack.stack.newScope();
        return i;
    }

    public static DSCP idType(String id){
        if(!STSStack.stack.searchID(id))
            throw new SemanticErrorException("");
        DSCP myType =  STSStack.stack.getDSCP(id);
        return myType.idType(id);
    }


    public static void extend(String id) {
        DSCP parent = STSStack.stack.getDSCP(id);
        parent.opExtend(STSStack.stack.searchClassType()); // shouldn't give null error if this method is called inside a classScope.
    }
}
