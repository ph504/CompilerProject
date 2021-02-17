package Model.cgen;

import Model.typeSystem.DSCP;

public class IntCgen extends ICGEN {

    @Override
    public void loadConstant(String constantValue) {
        int cstval = Integer.parseInt(constantValue);
        textSeg += ("\taddi $t3, $zero, " + cstval + "\n");
    }

    @Override
    public void assignment(DSCP lval) {
        //System.out.println("inja bod!");

        if (!dataSeg.contains("arthStackPtr"))
        {
            dataSeg += ("\tarthStackPtr:   .word   268501344\n");
        }

        textSeg += ("\tadd $t4, $zero, $t3\t\t\t\t# int assignment\n");

        textSeg += "\tla $t5, " + lval.getAddress() + "\n";                // az mem makan variable ro behesh point mikonim.
        textSeg += "\tsw $t4, 0($t5)\n";                            // megdar mohasebat ra in ja to variable assign mikonim.
    }

    @Override
    public String getMem() {
        return null;
    }
}
