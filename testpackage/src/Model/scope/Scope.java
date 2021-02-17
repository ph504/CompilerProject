package Model.scope;

import Model.typeSystem.ClassType;
import Model.typeSystem.DSCP;
import Model.typeSystem.FunctionType;

import java.util.ArrayList;
import java.util.List;

class Scope { // this class is purely implemented for Spaghetti stack.

    private Scope parentScope;
    private List<Scope> children = new ArrayList<>();
    private int index = 0;
    private SymbolTable st = new SymbolTable();
    private static final Scope root = new Scope(null);

    Scope(Scope parentScope) {
        this.parentScope = parentScope;
    }

    static Scope getRoot(){
        return root;
    }

    Scope getParentScope() {
        return parentScope;
    }

    public void setParentScope(Scope parentScope) {
        this.parentScope = parentScope;
    } // this is used for inheritance.

    void addChild(Scope scope){
        children.add(scope);
    }

    SymbolTable getSymbolTable() {
        return st;
    }

    public DSCP getDSCP(String id){
        return st.getDSCP(id);
    }

    String getClassIDScope(){ // gives the id of the Model.scope which we are in it and it is a Model.scope of a class. // it is overriden by ClassScope.
        return null;
    }

    ClassType getClassType(){
        return null;
    }

    FunctionType getFunctionType(){return null;}

    String getFunctionIDScope(){return null;}

    boolean isLoop(){return false;}

    public void resetIndex(){ // it is used when exiting scope.
        index = 0;
    }

    public Scope next(){
        return children.get(index++);
    }

    // get these followed methods to getThisClassType and getThisClassID. and implement the methods for get super class. maybe if necessary.
    //

}
