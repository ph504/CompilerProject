package Model.cgen;

import Model.scope.STSStack;
import Model.typeSystem.DSCP;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;


public abstract class ICGEN
{
    String arthmem;
    static String dataSeg = ".data\n";
    static String textSeg = ".text\n" + "main:\n";
    static int op1 = 1;
    static int op2 = 2;
    static int doubleCount = 0;
    static int continueCount = 0;
    static int lableCount = 0;
    static int converToFalseCount = 0;

    static Stack<String> Memory = new Stack<String>();
    static List<String> etc_Free = Arrays.asList(
            "$zero","pc","hi","lo","$at","$v0","$v1","$sp","$fp","$ra","$k0","$k1","$gp"
    );

    static List<String> arithmeticRegisters_Free = Arrays.asList(
            "$t0","$t1","$t2","$t3","$t4","$t5","$t6","$t7","$t8","$t9","$s0","$s1","$s2","$s3","$s5","$s6","$s7"
    );

    static List<String> coproc1_Free = Arrays.asList(
            "$f0" ,"$f1" ,"$f2" ,"$f3" ,"$f4" ,"$f5" ,"$f6" ,"$f7" ,"$f8" ,"$f9" ,"$f10","$f11","$f12","$f13",
            "$f14","$f15","$f16","$f17","$f18","$f19","$f20","$f21","$f22","$f23","$f24","$f25","$f26","$f27",
            "$f28", "$f29","$f30","$f31"
    );

    static List<String> functionParameter_Free = Arrays.asList(
            "$a0","$a1","$a2","$a3"
    );

    static List<String> etc_Occupied = new ArrayList<String>();

    static List<String> arithmeticRegisters_Occupied = new ArrayList<String>();

    static List<String> coproc1_Occupied = new ArrayList<String>();

    static List<String> functionParameter_Occupied = new ArrayList<String>();

    public static String assignRegister(List<String> Free , List<String> Occupied)
    {
        String assignedRegister = Free.remove(0);
        Occupied.add(assignedRegister);

        return assignedRegister;
    }

    public static String freeRegister(List<String> Free , List<String> Occupied)
    {
        String freedRegister = Occupied.remove(0);
        Occupied.add(freedRegister);

        return freedRegister;
    }

    //------------------------------------ integer -------------------------------------------------------------
    public static void PlusIntGen(){

        if (!dataSeg.contains("arthStackPtr")){
            dataSeg += ("\tarthStackPtr:   .word   268501344\n");
        }

        textSeg += ("\tadd $t3, $t1, $t2\t\t\t\t# int plus. \n");

    }

    public static void pushMips(/*Integer exp*/){

        if (!textSeg.contains("\tla $t0, arthStackPtr\n")) {

            textSeg += ("\tla $t0, arthStackPtr\n");

//            textSeg += ("   la $t0, arthStackPtr\n" +             // shayad niyaz bashad..
//                    "   addi $t3, $zero," + exp.intValue() + "\n");

        }
//       else
//       {
//           textSeg += ( "   addi $t3, $zero, " + exp.intValue()  + "\n");
//       }

        textSeg += ("\tsw $t3, 0($t0)\n"
                + "\taddi $t0, $t0, 4\t\t\t\t# push int mips.\n");
    }


    public static void popMips(int regNum){

        textSeg += ("\tsub $t0, $t0, 4\n"
                + "\tlw $t" + regNum + ", 0($t0)\t\t\t\t# pop int mips.\n") ;
    }

    public static void declIntCgen(String ID){

        DSCP d = STSStack.stack.getDSCP(ID);             // search and find var using its id.
        d.setAddress(ID + op1);                         //insert label in symbol table (in ids dcsp's address field).

        //d.setAddress(Addresss1);
        dataSeg += ("\t" + d.getAddress() + ": .word" + " 0\t\t\t\t# int decl.\n");
        op1++;
    }

    public static void fetchRVal(DSCP rval){
        // if (SymbolTable.st.)
        // rval.getAddress() -> label variable to mips midahad.
        textSeg += ("\tla $t5, " +  rval.getAddress() + "\t\t\t\t# rval fetch op.\n");
        textSeg += ("\tlw $t3, 0($t5) \n");
    }

    public static void MinusIntGen(){

        if (!dataSeg.contains("arthStackPtr")){
            dataSeg += ("\tarthStackPtr:   .word   268501344\n");
        }

        textSeg += ("\tsub $t3, $t1, $t2\t\t\t\t# int subtract. \n");

    }

    public static void uMinusIntGen()
    {
        if (!dataSeg.contains("arthStackPtr"))
        {
            dataSeg += ("\tarthStackPtr:   .word   268501344\n");
        }
        textSeg += ("\tsub $t3, $zero, $t1 \t\t\t\t# int uminus.\n");
    }

    public static void multIntGen()
    {
        if (!dataSeg.contains("arthStackPtr"))
        {
            dataSeg += ("\tarthStackPtr:   .word   268501344\n");
        }
        textSeg += ("\tmult $t1, $t2\t\t\t\t# int mult.\n");

        textSeg += ("\tmflo $t3\n");
    }

    public static void DivIntGen()
    {
        if (!dataSeg.contains("arthStackPtr"))
        {
            dataSeg += ("\tarthStackPtr:   .word   268501344\n");
        }

        textSeg += ("\tdiv $t1, $t2\t\t\t\t# int div.\n");

        textSeg += ("\tmflo $t3\n");
    }

    public static void ModIntGen()
    {
        if (!dataSeg.contains("arthStackPtr"))
        {
            dataSeg += ("\tarthStackPtr:   .word   268501344\n");
        }
        textSeg += ("\tdiv $t1, $t2\t\t\t\t# int mod.\n");

        textSeg += ("\tmfhi $t3\n");
    }

    public static void intBooleanOprationsCgen(String opSem){

        if (!dataSeg.contains("arthStackPtr"))
        {
            dataSeg += ("\tarthStackPtr:   .word   268501344\n");
        }

        switch (opSem){

            case ">":

                textSeg += "\tslt $t3, $t2, $t1\t\t\t\t#GT.\n";

                break;

            case "<":
                textSeg += "\tslt $t3, $t1, $t2\t\t\t\t#LT.\n";

                break;

            case ">=":

                textSeg += "\tslt $t3, $t1, $t2\n\tbne $t3, $zero, label" + lableCount + "\n";
                textSeg += "\taddi $t3, $zero, 1\n\t j continue" + continueCount +
                        "\n\tlabel" + (lableCount++) + ":" + "\n\taddi $t3, $zero, 0\n\tcontinue" + (continueCount++) + ":\n";

                break;

            case "<=":

                textSeg += "\tslt $t3, $t2, $t1\n\tbne $t3, $zero, label" + lableCount + "\n";
                textSeg += "\taddi $t3, $zero, 1\n\t j continue" + continueCount +
                        "\n\tlabel" + (lableCount++) + ":" + "\n\taddi $t3, $zero, 0\n\tcontinue" + (continueCount++) + ":\n";
                break;

            case "==":
                textSeg += "\tbne $t1, $t2,label" + lableCount + "\n\taddi $t3, $zero, 1\t\t\t\t#2 oprands are equal.\n"
                        + "\tj continue" + continueCount;
                textSeg += "\n\tlabel" + (lableCount++) + ":\n\taddi $t3, $zero, 0\t\t\t\t#2 oprands are not equal.\n"
                        + "\tcontinue" + (continueCount++)+ ":\n";

                break;

            case "!=":

                textSeg += "\tbne $t1, $t2,label" + lableCount + "\n\taddi $t3, $zero, 0\t\t\t\t#2 oprands are equal.\n"
                        + "\tj continue" + continueCount;
                textSeg += "\n\tlabel" + (lableCount++) + ":\n\taddi $t3, $zero, 1\t\t\t\t#2 oprands are not equal.\n"
                        + "\tcontinue" + (continueCount++)+ ":\n";

                break;
        }
    }

    //Double Type Operations
    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //$f0 and $f2 are for temp operand values
    //$f4 is the temp value that we push to stack everytime
    //$f6 is the final value that we assign

//Double Type Operations
//------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------
//$f0 and $f2 are for temp operand values
//$f4 is the temp value that we push to stack everytime
//$f6 is the final value that we assign

    public static void PlusDoubleGen()
    {
        if (!dataSeg.contains("arthDoubleStackPtr"))
        {
            dataSeg += ("\tarthDoubleStackPtr: .word 268501152\n");
        }
        textSeg += ("\tadd.d $f4, $f0, $f2\n");
    }

    public static void MinusDoubleGen()
    {
        if (!dataSeg.contains("arthDoubleStackPtr"))
        {
            dataSeg += ("\tarthDoubleStackPtr: .word 268501152\n");
        }
        textSeg += ("\tsub.d $f4, $f0, $f2\n");
    }

    public static void UMinusDoubleGen()
    {
        if (!dataSeg.contains("arthDoubleStackPtr"))
        {
            dataSeg += ("\tarthDoubleStackPtr: .word 268501152\n");
        }
        textSeg += ("\tsub $f4, $zero, $f0 \n");
    }

    public static void MultDoubleGen()
    {
        if (!dataSeg.contains("arthDoubleStackPtr"))
        {
            dataSeg += ("\tarthDoubleStackPtr: .word 268501152\n");
        }
        textSeg += ("\tmul.d $f4 , $f0 , $f2\n");
    }

    public static void DivDoubleGen()
    {
        if (!dataSeg.contains("arthDoubleStackPtr"))
        {
            dataSeg += ("\tarthDoubleStackPtr: .word 268501152\n");
        }
        textSeg += ("\tdiv.d $f4 , $f0 , $f2\n");
    }

    public static void ModDoubleGen()
    {
        if (!dataSeg.contains("arthDoubleStackPtr"))
        {
            dataSeg += ("\tarthDoubleStackPtr: .word 268501152\n");
        }
        textSeg += ("\tdiv.d $f4 , $f0 , $f2\n");
        textSeg += ("\tcvt.w.d $f4 , $f4\n");
        textSeg += ("\tmfc1 $t7 , $f4\n");
        textSeg += ("\tmtc1 $t7 , $f4\n");
        textSeg += ("\tcvt.d.w $f4, $f4\n");
        textSeg += ("\tmul.d $f4 , $f4 , $f2\n");
        textSeg += ("\tsub.d $f4 , $f0 , $f4\n");
    }

    public static void DTOIGen() //double to integer
    {
        if (!dataSeg.contains("arthDoubleStackPtr"))
        {
            dataSeg += ("\tarthDoubleStackPtr: .word 268501152\n");
        }
        textSeg += ("\tcvt.w.d $f4 , $f4\n");
        textSeg += ("\tmfc1 $t3 , $f4\n");
    }

    public static void pushMipsDouble()
    {
        if (!textSeg.contains("\tla $t0, arthDoubleStackPtr\n"))
        {
            textSeg += ("\tla $t0, arthDoubleStackPtr\n");
        }
        textSeg += ("\ts.d $f4, 0($t0)\n"
                + "\taddi $t0, $t0, 4\n");
    }

    public static void popMipsDouble(int regNum)
    {
        textSeg += ("\tsub $t0, $t0, 4\n"
                + "\tl.d $f" + regNum + ", 0($t0)\n") ;
    }

    public static void declDoubleCgen(String ID){

        DSCP d = STSStack.stack.getDSCP(ID); // search and find var using its id.
        d.setAddress(ID + op1); //insert label in symbol table (in ids dcsp's address field).

//d.setAddress(Addresss1);
        dataSeg += ("\t" + d.getAddress() + ": .double" + " 0\t\t\t\t# double decl.\n");
        op1++;
    }

    //------------------------------------------------------ boolean ----------------------------------------------------------------

    public static void itobCgen(){

        if (!dataSeg.contains("arthStackPtr"))
        {
            dataSeg += ("\tarthStackPtr:   .word   268501344\n");
        }

        textSeg +=
                "\tbeqz $t1, convertToFalse" + (converToFalseCount++) +"\n" + "\t\taddi $t3,  $zero, 1\n\t\tj continue"+ (continueCount) + "\n"
                        + "\tconvertToFalse:\n\t\tadd $t3, $zero, $zero" +
                        "\ncontinue" + (continueCount++) + ":\n";
    }

    public static void booleanOprations(String opSem){

        switch (opSem){
            case "&&":

                textSeg +="\tand $t3, $t1, $t2\t\t\t\t#expr and.\n";

                break;

            case "||":

                textSeg +="\tor $t3, $t1, $t2\t\t\t\t#expr or.\n";

                break;

            case "!":

                textSeg +="\taddi $t2, $zero, 0x00000001\n" +
                        "\tnor $t3, $t1, $t1\t\t\t\t#expr not.\n\tand $t3, $t3, $t2\n";
                break;
        }

    }

//    public static void btoiCgen(){
//
//        textSeg +=
//
//    }



    public static void compile() throws IOException {
        FileWriter out = new FileWriter("out.asm");
        System.out.println(dataSeg + textSeg);
        out.write(dataSeg + textSeg);
        out.close();
    }



//    public static void functionDecleration(String functionID, String fLabel, function parameters?){
//
//    }

//    public static void functionCall(ClassType ct){         // ## handlee
//
//    }


    //========================================== Arya ==========================================//
    public abstract void loadConstant(String constantValue);
    public abstract void assignment(DSCP lval);
    public abstract String getMem();

}
