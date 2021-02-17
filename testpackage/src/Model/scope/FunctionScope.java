package Model.scope;


import Model.typeSystem.FunctionType;

class FunctionScope extends Scope{
    private String functionID; // this is the function name which we are in the Model.scope of.

    FunctionScope(String functionID, Scope parentScope) {
        super(parentScope);
        this.functionID = functionID;
    }

    @Override
    FunctionType getFunctionType(){return (FunctionType) getSymbolTable().getDSCP(functionID);} // super is because that the id is stored in the parent scope but we are in the function scope ourselves.

    @Override
    String getFunctionIDScope() {
        return functionID;
    }
}
