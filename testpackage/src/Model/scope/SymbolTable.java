package Model.scope;

import Model.typeSystem.DSCP;

import java.util.HashMap;

class SymbolTable {

    /*public static final SymbolTable st = new SymbolTable();*/

    SymbolTable(){}

    private final HashMap<String, DSCP> table = new HashMap<>();   // key id name.


    void addEntry(String id, DSCP dscp){
        table.put(id, dscp);
    }

    public DSCP getDSCP(String id){   // we get specified entry using identifirs String name in hashTable.
        return table.get(id);
    }

    boolean contains(String id){
        return table.containsKey(id);
    }

}