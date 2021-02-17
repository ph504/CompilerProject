package Model.typeSystem;


import Model.scope.VTable;
import java.util.LinkedList;
import java.util.List;

public class ClassType extends DSCP {

    // the following is unnecessary due to the existence of type variable
    // public String classID; // the name of the class

    private VTable vtable;
    public final List<DSCP> fields = new LinkedList<>();
    public DSCP parent;

    //idea for Interface implementation:
    public final List<InterfaceType> iparents = new LinkedList<>();

    public ClassType(String className){super(className);}

    @Override
    public boolean isTypeof(DSCP dt) {
        return dt instanceof ClassType;
    }

    public void setParent(DSCP parent) {
        this.parent = parent;
    } // maybe later consider replacing with inherit/extend operation.

    public void setIParents(List<InterfaceType> iparents){
        this.iparents.addAll(iparents);
    }

    @Override
    public boolean isConvertible(DSCP dt) {

        /*check each parent and if it was not return false and if all it was one of them return true*/
        boolean flag = false;
        for (InterfaceType i :iparents) {
            if (i.type.equals(dt.type)) {
                flag = true;
            }
        }
        return type.equals(dt.type) || parent.type.equals(dt.type) || flag;
    }

    /*@Override
    public int getSize() {
        return ;
    }*/


    @Override
    public DSCP opNew() {
        // search if the identifier is defined first and then return the respective Type.
        // maybe some operation for vtable also needed here.
        return this; // idk if it works.
    }

    @Override
    public DSCP pushStack() {

        return this;
    }

    @Override
    public void addMember(DSCP ft) {
        fields.add(ft);
    }

    @Override
    public DSCP createType() {
        return this;
    } // maybe classID should be highly considered as well.

    public void addMembers(List<DSCP> ft){
        fields.addAll(ft);
    }

    public void addMemFunction(String funcID){
        vtable.addMember(funcID);
    }

    public String getLabel(String funcID){
        return vtable.getLabel(funcID);
    }

    public boolean containsFunction(String funcID){
        return vtable.wasDeclBefore(funcID);
    }

    @Override
    public DSCP idType(String id) {
        return new ClassType(id);
    }

    @Override
    public void opExtend(ClassType child) {
        child.setParent(this);
    }
}
