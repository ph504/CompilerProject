package Model.scope;

import Model.typeSystem.ClassType;

class ClassScope extends Scope{ // possible usage for "THIS" operator.
    private String classID; // this is the class name which we are in the scope of.

    /*public ClassScope(Scope parentScope) {
        super(parentScope);
    }*/

    public ClassScope(String classID, Scope parentScope){
        super(parentScope);
        this.classID = classID;
    }

    @Override
    ClassType getClassType() { // gives the ClassType based on the classID in the Model.scope from the symbol table.
        return (ClassType) getSymbolTable().getDSCP(classID); // shouldn't give error.
    } // super is because that the id is stored in the parent scope but we are in the class scope ourselves.
}
