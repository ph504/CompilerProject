package Model.scope;

import java.util.HashMap;

public class VTable {

    private HashMap<String, String> nameLabelTable; // to be decided how to implement // -> func-name, labal-name for mips.
    static int labelCounter = 1;   // for creating label for mips code-gen.

    public void addMember(String funcID){
        /*if(wasDeclBefore(funcID))
            return // should give error here if the function with the ID already exists.

        nameLabelTable.put(funcID, createLabel(funcID));*/
    }

    private String createLabel(String ID){

        return (ID + labelCounter++);     //**** for better categorization,  we can concat name of the class to..
    }

    public String getLabel(String funcID){

        return nameLabelTable.get(funcID);
    }

    public boolean wasDeclBefore(String funcID){
        return nameLabelTable.containsKey(funcID);
    }
}
