package Model.scope;


import Model.multipass.IPass;
import Model.typeSystem.ClassType;
import Model.typeSystem.DSCP;
import Model.typeSystem.FunctionType;
import Model.typeSystem.SemanticErrorException;


import java.util.HashMap;

public class STSStack{

    public static final HashMap<Scope,String> giveScopeGetID = new HashMap<>(); // this map saves the scopes for each ID of the classes/function defined.
    public static STSStack stack = new STSStack(); // singleton pattern
    private Scope currentPtr;



    public STSStack() {
        currentPtr = Scope.getRoot();
    }

    public void nextScope(){
        String pass = IPass.pass;
        Scope root = Scope.getRoot();
        currentPtr = currentPtr.next();
    }

    public void newScope() {
        Scope temp = new Scope(currentPtr);
        currentPtr.addChild(temp);
        currentPtr = temp;
    }

    public void newClassScope(String classID){
        Scope temp = new ClassScope(classID, currentPtr);
        currentPtr.addChild(temp);
        currentPtr = temp;
    }

    public void newFunctionScope(String funcID){
        Scope temp = new FunctionScope(funcID, currentPtr);
        currentPtr.addChild(temp);
        currentPtr = temp;
    }

    public void newLoopScope(){
        Scope temp = new LoopScope(currentPtr);
        currentPtr.addChild(temp);
        currentPtr = temp;
    }

    public void exitScope() {
        /*
        * removes the top of the stack but it actually changes it back to the parent Node instead of completely removing it from the ds.
        * it doesn't remove it because it wants to provide the compiler multi-pass processing on the stack. it will be used for future needs.*/
        Scope root = Scope.getRoot();
        currentPtr = currentPtr.getParentScope();
        currentPtr.resetIndex();
    }

    public Scope peek() {
        /*
        * takes the current Nods i.e. top of the stack and returns it. leaving it unchanged.*/
        return currentPtr;
    }

    public boolean searchID(String id) {
        Scope temp = currentPtr;
        while(!temp.equals(Scope.getRoot())){
            if(temp.getSymbolTable().contains(id))
                return true;
            temp = temp.getParentScope();
        }

        return temp.getSymbolTable().contains(id); // temp == Scope.root
    }

    public DSCP getDSCP(String id){

        /*
        * finds the corresponding Model.scope in which the id was first declared. returns ErrorType if it doesn't find any.*/
        Scope temp = currentPtr;
        while(!temp.equals(Scope.getRoot())){
            if(temp.getSymbolTable().contains(id))
                return temp.getDSCP(id);
            temp = temp.getParentScope();
        }

        if(temp.getSymbolTable().contains(id)) // temp == Scope.root
            return temp.getDSCP(id);

        throw new SemanticErrorException("");
    }

    public void addToST(String id, DSCP dscp){

        /*
        * adds the entry to the symbol table
        * id is the identifier
        * dscp is the type which was instantiated in the cup.*/
        currentPtr.getSymbolTable().addEntry(id, dscp);
    }

    public ClassType searchClassType(){

        /*
        * first it gets the top of the spaghetti stack
        * second it finds the corresponding id of the class in which the current Model.scope is.
        * third it gets the id to take the description of the id related to its entry in the symbol table
        * fourth it returns the classType of it and if the downCast was unsuccessful the error is very well done.*/
        // return (ClassType) STSStack.stack.getDSCP(STSStack.giveScopeGetID.get(STSStack.stack.peek()));
        // this isn't useful anymore. so it is subject to deletion sooner or later.
        Scope temp = currentPtr;
        while (!temp.equals(Scope.getRoot())){

            ClassType ct = temp.getClassType();
            if (ct==null) {
                temp = temp.getParentScope();
            }
            else
                return ct;
        }

        return null; // there is no class Model.scope.
    }

    public String searchClassID() {

        // does the same as the function above but it gives the name of the class in the Model.scope instead.
        Scope temp = currentPtr;
        while (!temp.equals(Scope.getRoot())) {

            String cid = temp.getClassIDScope();
            if (cid == null) { // the Model.scope isn't a class Model.scope.
                temp = temp.getParentScope();
            } else
                return cid; // found the class Model.scope.
        }

        return temp.getClassIDScope(); // temp == Scope.root, if cid == null --> no class Model.scope found.
    }

    public FunctionType searchFunctionType(){
        /*
         * first it gets the top of the spaghetti stack
         * second it finds the corresponding id of the function in which the current Model.scope is.
         * third it gets the id to take the description of the id related to its entry in the symbol table
         * fourth it returns the functionType of it and if the downCast was unsuccessful the error is very well done.*/
        // return (FunctionType) STSStack.stack.getDSCP(STSStack.giveScopeGetID.get(STSStack.stack.peek()));
        // this isn't useful anymore. so it is subject to deletion sooner or later.
        Scope temp = currentPtr;
        while (!temp.equals(Scope.getRoot())){

            FunctionType ft = temp.getFunctionType();
            if (ft==null) {
                temp = temp.getParentScope();
            }
            else
                return ft;
        }

        return temp.getFunctionType(); // temp == Scope.root, if ct == null --> there is no function scope.
    }

    public String searchFunctionID(){
        // does the same as the function above but it gives the name of the function in the Model.scope instead.
        Scope temp = currentPtr;
        while (!temp.equals(Scope.getRoot())) {

            String fid = temp.getFunctionIDScope();
            if (fid == null) { // the scope isn't a function scope.
                temp = temp.getParentScope();
            } else
                return fid; // found the function scope.
        }

        return temp.getFunctionIDScope(); // temp == Scope.root, if fid == null --> no function scope found.
    }

    public boolean searchLoop(){
        // does the same as the function above but it gives the name of the function in the Model.scope instead.
        Scope temp = currentPtr;
        while (!temp.equals(Scope.getRoot())) {

            boolean loop = temp.isLoop();
            if (!loop) { // the Model.scope isn't a loop scope.
                temp = temp.getParentScope();
            } else
                return loop; // found the loop scope.
        }

        return temp.isLoop(); // temp == Scope.root, if fid == null --> no loop scope found.
    }

    /*public FunctionType getFunctionType(){
        return currentPtr.getFunctionType();
    }*/

    /*public String getFunctionID(){
        return currentPtr.getFunctionIDScope();
    }*/
}
