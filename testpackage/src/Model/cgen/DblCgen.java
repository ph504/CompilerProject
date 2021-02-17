package Model.cgen;

import Model.typeSystem.DSCP;

public class DblCgen extends ICGEN {

    private static final String dblMem = "268501152";
    DblCgen(String mem){
        arthmem = mem;
    }
    DblCgen(){
        arthmem = "268501152";
    }
    @Override
    public void loadConstant(String constantValue) {
        double cstval = Double.parseDouble(constantValue);
        if (!dataSeg.contains("arthDoubleStackPtr"))
        {
            dataSeg += ("\tarthDoubleStackPtr: .word "+dblMem +"\n");
        }
        dataSeg += ("\tdoubleConst" + doubleCount + ": .double " + cstval + "\n");
        textSeg += ("\tl.d $f4 , doubleConst" + doubleCount + "\n") ;
    }

    @Override
    public void assignment(DSCP lval) {
        textSeg += ("\tadd.d $f6, $f30, $f4\t\t\t\t# double assignment\n");

        textSeg += "\tla $t5, " + lval.getAddress() + "\n"; // az mem makan variable ro behesh point mikonim.
        textSeg += "\ts.d $f6, 0($t5)\n"; // megdar mohasebat ra in ja to variable assign mikonim.
    }

    @Override
    public String getMem() {
        return null;
    }


}
