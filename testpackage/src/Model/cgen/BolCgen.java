package Model.cgen;

import Model.typeSystem.DSCP;

public class BolCgen extends ICGEN{

    @Override
    public void loadConstant(String constantValue) {
        boolean cstval = Boolean.parseBoolean(constantValue);
        if (!dataSeg.contains("arthStackPtr"))
        {
            dataSeg += ("\tarthStackPtr:   .word   268501344\n");
        }

        if (cstval) {
            textSeg += ("\taddi $t3, $zero, 1\t\t\t\t# load costant bool(true).\n");
        }

        else {
            textSeg += ("\taddi $t3, $zero, 0\t\t\t\t# load costant bool(false).\n");
        }
    }

    @Override
    public void assignment(DSCP lval) {

    }

    @Override
    public String getMem() {
        return null;
    }
}
