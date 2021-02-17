package Model;

import Model.scope.STSStack;
import Model.typeSystem.DSCP;


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CodeGen
{

    private static final Stack<String> fuexpStack = new Stack<>();
    public static String dataSeg = ".data\n";
    public static String textSeg = ".text\n" + "main:\n";
    public static String FUEXP = ""; // fuexp = stands for "for's update expression".
    public static boolean inFUEXP = false;


    public static int op1 = 1;
    public static int op2 = 2;
    public static int doubleCount = 0;
    public static int continueCount = 0;
    public static int labelCount = 0;

    public static int ifLabelCount = 0;
    public static int ifMaxLabelCount = 0;
    public static int ifIndexLabelCount = 0;

    public static int loopLabelCount = 0;
    public static int loopMaxLabelCount = 0;
    public static int loopIndexLabelCount = 0;

    public static int converToFalseCount = 0;

    public static int inputNumberCount = 0;
    public static int ReadIntCount = 0;
    public static int opReadIntCompleteCount = 0;
    public static int badCharInputCount = 0;
    public static int readIntCount = 0;
    public static int inputStringCount = 0;
    public static int strConstantCount = 0;
    public static int stringRef = 0;
    public static int lenCalculationCount = 0;
    public static int endLenCalculationCount = 0;
    public static int concatCount = 0;
    public static int endConcatCount = 0;
    public static List<String> functionList = new ArrayList<>();




    //------------------------------------ integer -------------------------------------------------------------
    public static void PlusIntGen()
    {
        if (!getTextSeg().contains("la $t0 , 268501344")){
            setTextSeg("\tla $t0 , 268501344\n");
        }

        setTextSeg("\tadd $t3, $t1, $t2\t\t\t\t# adding two integers. \n");

        setTextSeg("\n");
    }

    public static void pushMips(int regnum)
    {
        if (!getTextSeg().contains("la $t0 , 268501344")){
            setTextSeg("\tla $t0 , 268501344\n");
        }
        setTextSeg("\tsw $t" + regnum +", 0($t0)\n"
                + "\taddi $t0, $t0, 4\t\t\t\t# push int mips.\n");

        setTextSeg("\n");
    }


    public static void popMips(int regNum){

        setTextSeg("\tsub $t0, $t0, 4\n" + "\tlw $t" + regNum + ", 0($t0)\t\t\t\t# pop int mips.\n") ;

        setTextSeg("\n");
    }

    public static void loadConstantIntCgen(Integer ConstantValue)
    {
        setTextSeg("\taddi $t3, $zero, " + ConstantValue + "\t\t\t\t# loading constant value\n");

        setTextSeg("\n");
    }

    public static void intAssignmentCgen(DSCP lval){
//System.out.println("inja bod!");

        if (!getTextSeg().contains("la $t0 , 268501344")){
            setTextSeg("\tla $t0 , 268501344\n");
        }

        setTextSeg("\tadd $t4, $zero, $t3\t\t\t\t# int assignment\n");

        setTextSeg("\tla $t5, " + lval.getAddress() + "\n"); // az mem makan variable ro behesh point mikonim.
        setTextSeg("\tsw $t4, 0($t5)\n"); // megdar mohasebat ra in ja to variable assign mikonim.

        setTextSeg("\n");
    }

    public static void declIntCgen(String ID){

        DSCP d = STSStack.stack.getDSCP(ID); // search and find var using its id.
        d.setAddress(ID + op1); //insert label in symbol table (in ids dcsp's address field).

        dataSeg += ("\t" + d.getAddress() + ": .word" + " 0\t\t\t\t# int decl.\n");
        op1++;

        setTextSeg("\n");
    }

    public static void fetchRVal(DSCP rval)
    {
        setTextSeg("\tla $t5, " + rval.getAddress() + "\t\t\t\t# rval fetch op.\n");
        setTextSeg("\tlw $t3, 0($t5) \n");
        pushMips(3);
    }

    public static void MinusIntGen()
    {
        if (!getTextSeg().contains("la $t0 , 268501344")){
            setTextSeg("\tla $t0 , 268501344\n");
        }

        setTextSeg("\tsub $t3, $t1, $t2\t\t\t\t# int subtract. \n");

        setTextSeg("\n");
    }

    public static void uMinusIntGen()
    {
        if (!getTextSeg().contains("la $t0 , 268501344")){
            setTextSeg("\tla $t0 , 268501344\n");
        }
        setTextSeg("\tsub $t3, $zero, $t1 \t\t\t\t# int uminus.\n");

        setTextSeg("\n");
    }

    public static void multIntGen()
    {
        if (!getTextSeg().contains("la $t0 , 268501344")){
            setTextSeg("\tla $t0 , 268501344\n");
        }
        setTextSeg("\tmult $t1, $t2\t\t\t\t# int mult.\n");

        setTextSeg("\tmflo $t3\n");

        setTextSeg("\n");
    }

    public static void DivIntGen()
    {
        if (!getTextSeg().contains("la $t0 , 268501344")){
            setTextSeg("\tla $t0 , 268501344\n");
        }

        setTextSeg("\tdiv $t1, $t2\t\t\t\t# int div.\n");

        setTextSeg("\tmflo $t3\n");

        setTextSeg("\n");
    }

    public static void ModIntGen()
    {
        if (!getTextSeg().contains("la $t0 , 268501344")){
            setTextSeg("\tla $t0 , 268501344\n");
        }
        setTextSeg("\tdiv $t1, $t2\t\t\t\t# int mod.\n");

        setTextSeg("\tmfhi $t3\n");

        setTextSeg("\n");
    }

    public static void intBooleanOprationsCgen(String opSem){

        if (!getTextSeg().contains("la $t0 , 268501344")){
            setTextSeg("\tla $t0 , 268501344\n");
        }

        switch (opSem){

            case ">":

                setTextSeg("\tslt $t3, $t2, $t1\t\t\t\t#GT.\n");

                break;

            case "<":
                setTextSeg("\tslt $t3, $t1, $t2\t\t\t\t#LT.\n");

                break;

            case ">=":

                setTextSeg("\tslt $t3, $t1, $t2\n\tbne $t3, $zero, label" + labelCount + "\n");
                setTextSeg("\taddi $t3, $zero, 1\n\t j continue" + continueCount +
                        "\n\tlabel" + (labelCount++) + ":" + "\n\taddi $t3, $zero, 0\n\tcontinue" + (continueCount++) + ":\n");

                break;

            case "<=":

                setTextSeg("\tslt $t3, $t2, $t1\n\tbne $t3, $zero, label" + labelCount + "\n");
                setTextSeg("\taddi $t3, $zero, 1\n\t j continue" + continueCount +
                        "\n\tlabel" + (labelCount++) + ":" + "\n\taddi $t3, $zero, 0\n\tcontinue" + (continueCount++) + ":\n");
                break;

            case "==":
                setTextSeg("\tbne $t1, $t2,label" + labelCount + "\n\taddi $t3, $zero, 1\t\t\t\t#2 oprands are equal.\n"
                        + "\tj continue" + continueCount);
                setTextSeg("\n\tlabel" + (labelCount++) + ":\n\taddi $t3, $zero, 0\t\t\t\t#2 oprands are not equal.\n"
                        + "\tcontinue" + (continueCount++)+ ":\n");

                break;

            case "!=":

                setTextSeg("\tbne $t1, $t2,label" + labelCount + "\n\taddi $t3, $zero, 0\t\t\t\t#2 oprands are equal.\n"
                        + "\tj continue" + continueCount);
                setTextSeg("\n\tlabel" + (labelCount++) + ":\n\taddi $t3, $zero, 1\t\t\t\t#2 oprands are not equal.\n"
                        + "\tcontinue" + (continueCount++)+ ":\n");

                break;
        }
        setTextSeg("\n");
    }

    public static void itodGen() //Integer to double
    {
        if (!getTextSeg().contains("la $t0 , 268501344")){
            setTextSeg("\tla $t0 , 268501344\n");
        }
        setTextSeg("\tmtc1 $t3, $f4\n");
        setTextSeg("\tcvt.s.w $f4, $f4\t\t\t\t #I To D\n");
        setTextSeg("\n");
    }

//Double Type Operations
//------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------
//$f0 and $f2 are for temp operand values
//$f4 is the temp value that we push to stack everytime
//$f6 is the final value that we assign

    public static void printInt(){

        popMips(3);
        setTextSeg("\tadd $a0, $zero, $t3\n");
        setTextSeg("\tli $v0, 1\n\tsyscall\n");
    }

    public static void printDouble()
    {
        popMipsDouble(4);

        setTextSeg("\tadd.s $f12, $f31, $f4\n");
        setTextSeg("\tli $v0, 2\n\tsyscall\n");

    }

public static void PlusDoubleGen()
{
    if (!getTextSeg().contains("la $t0 , 268501152")){
        setTextSeg("\tla $t0 , 268501152\n");
    }
    setTextSeg("\tadd.s $f4, $f0, $f2\t\t\t\t # Addition of two Double values");

    setTextSeg("\n");
}

public static void MinusDoubleGen()
{
    if (!getTextSeg().contains("la $t0 , 268501152")){
        setTextSeg("\tla $t0 , 268501152\n");
    }
    setTextSeg("\tsub.s $f4, $f0, $f2\n");

    setTextSeg("\n");
}

public static void UMinusDoubleGen()
{
    if (!getTextSeg().contains("la $t0 , 268501152")){
        setTextSeg("\tla $t0 , 268501152\n");
    }
    setTextSeg("\tsub.s $f4, $f30, $f0 \n");

    setTextSeg("\n");
}

public static void MultDoubleGen()
{
    if (!getTextSeg().contains("la $t0 , 268501152")){
        setTextSeg("\tla $t0 , 268501152\n");
    }
    setTextSeg("\tmul.s $f4 , $f0 , $f2\n");

    setTextSeg("\n");
}

public static void DivDoubleGen()
{
    if (!getTextSeg().contains("la $t0 , 268501152")){
        setTextSeg("\tla $t0 , 268501152\n");
    }
    setTextSeg("\tdiv.s $f4 , $f0 , $f2\n");

    setTextSeg("\n");
}

public static void ModDoubleGen()
{
    if (!getTextSeg().contains("la $t0 , 268501152")){
        setTextSeg("\tla $t0 , 268501152\n");
    }
    setTextSeg("\tdiv.s $f4 , $f0 , $f2\n");
    setTextSeg("\tcvt.w.s $f4 , $f4\n");
    setTextSeg("\tmfc1 $t7 , $f4\n");
    setTextSeg("\tmtc1 $t7 , $f4\n");
    setTextSeg("\tcvt.s.w $f4, $f4\n");
    setTextSeg("\tmul.s $f4 , $f4 , $f2\n");
    setTextSeg("\tsub.s $f4 , $f0 , $f4\n");

    setTextSeg("\n");
}

public static void dtoiGen() //double to integer
{
    if (!getTextSeg().contains("la $t0 , 268501152")){
        setTextSeg("\tla $t0 , 268501152\n");
    }
    setTextSeg("\tround.w.s $f4 , $f4\n");
    setTextSeg("\tmfc1 $t3 , $f4\n");

    setTextSeg("\n");
}

public static void pushMipsDouble(int regNum)
{
    if (!getTextSeg().contains("la $t0 , 268501152")){
        setTextSeg("\tla $t0 , 268501152\n");
    }
    setTextSeg("\ts.s $f" + regNum + ", 0($t0)\n"
            + "\taddi $t0, $t0, 4\t\t\t\t# push double mips.\n");

    setTextSeg("\n");
}

public static void popMipsDouble(int regNum)
{
    setTextSeg("\tsub $t0, $t0, 4\n" + "\tl.s $f" + regNum + ", 0($t0)\t\t\t\t# pop double mips.\n") ;

    setTextSeg("\n");
}

public static void loadConstantDoubleCgen(Double ConstantValue)
{
    if (!dataSeg.contains("arthDoubleStackPtr"))
    {
        dataSeg += ("\tarthDoubleStackPtr: .word 268501152\n");
    }
    dataSeg += ("\tdoubleConst" + doubleCount + ": .float " + ConstantValue + "\n");
    setTextSeg("\tl.s $f4 , doubleConst" + doubleCount + "\n") ;
    doubleCount++;

    setTextSeg("\n");
}

public static void doubleAssignmentCgen(DSCP lval){

    setTextSeg("\tadd.s $f6, $f30, $f4\t\t\t\t# double assignment\n");

    setTextSeg("\tla $t5, " + lval.getAddress() + "\n"); // az mem makan variable ro behesh point mikonim.
    setTextSeg("\ts.s $f6, 0($t5)\n"); // megdar mohasebat ra in ja to variable assign mikonim.

    setTextSeg("\n");
}

public static void declDoubleCgen(String ID){

    DSCP d = STSStack.stack.getDSCP(ID); // search and find var using its id.
    d.setAddress(ID + op1); //insert label in symbol table (in ids dcsp's address field).

//d.setAddress(Addresss1);
    dataSeg += ("\t" + d.getAddress() + ": .float" + " 0.0\t\t\t\t# double decl.\n");
    op1++;

    setTextSeg("\n");
}

public static void fetchRValDouble(DSCP rval){
// if (SymbolTable.st.)
// rval.getAddress() -> label variable to mips midahad.
    setTextSeg("\tla $t5, " + rval.getAddress() + "\t\t\t\t# rval fetch double op.\n");
    setTextSeg("\tl.s $f4, 0($t5) \n");
    pushMipsDouble(4);

    setTextSeg("\n");
}

public static void doubleBooleanOprationsCgen(String opSem){

    if (!getTextSeg().contains("la $t0 , 268501344")){
        setTextSeg("\tla $t0 , 268501344\n");
    }
    setTextSeg ("\n #comparing doubles : \n");
    switch (opSem){

        case ">":
            setTextSeg("\tc.le.s $f0, $f2\n ");
            setTextSeg("\tbc1t isLE" + labelCount + "\n");
            setTextSeg("\tisGT" + labelCount + ": \n");
            setTextSeg("\taddi $t3 , $zero , 1\n");
            setTextSeg("\tj continue" + continueCount + "\n");
            setTextSeg("\tisLE" + (labelCount++) + ": \n");
            setTextSeg("\taddi $t3 , $zero , 0\n");
            setTextSeg("\tcontinue" + (continueCount++) + ": \n");
            break;

        case "<":
            setTextSeg("\tc.lt.s $f0, $f2\n");
            setTextSeg("\tbc1t isLT" + labelCount + "\n");
            setTextSeg("\tisGE" + labelCount + ": \n");
            setTextSeg("\taddi $t3 , $zero , 0\n");
            setTextSeg("\tj continue" + continueCount + "\n");
            setTextSeg("\tisLT" + (labelCount++) + ": \n");
            setTextSeg("\taddi $t3 , $zero , 1\n");
            setTextSeg("\tcontinue" + (continueCount++) + ": \n");
            break;

        case ">=":

            setTextSeg("\tc.lt.s $f0, $f2\n");
            setTextSeg("\tbc1t isLT" + labelCount + "\n");
            setTextSeg("\tisGE" + labelCount + ": \n");
            setTextSeg("\taddi $t3 , $zero , 1\n");
            setTextSeg("\tj continue" + continueCount + "\n");
            setTextSeg("\tisLT" + (labelCount++) + ": \n");
            setTextSeg("\taddi $t3 , $zero , 0\n");
            setTextSeg("\tcontinue" + (continueCount++) + ": \n");
            break;

        case "<=":

            setTextSeg("\tc.le.s $f0, $f2\n ");
            setTextSeg("\tbc1t isLE" + labelCount + "\n");
            setTextSeg("\tisGT" + labelCount + ": \n");
            setTextSeg("\taddi $t3 , $zero , 0\n");
            setTextSeg("\tj continue" + continueCount + "\n");
            setTextSeg("\tisLE" + (labelCount++) + ": \n");
            setTextSeg("\taddi $t3 , $zero , 1\n");
            setTextSeg("\tcontinue" + (continueCount++) + ": \n");
            break;


        case "==":
            setTextSeg("\tc.eq.s $f0, $f2\n");
            setTextSeg("\tbc1t isEqualDouble" + labelCount + "\n");
            setTextSeg("\tj isNotEqualDouble" + continueCount + "\n");
            setTextSeg("\tisEqualDouble" + labelCount + ": \n");
            setTextSeg("\tli $t3 , 1\n");
            setTextSeg("\tj continue" + continueCount + "\n");
            setTextSeg("\tisNotEqualDouble" + (labelCount++) + ": \n");
            setTextSeg("\tli $t3 , 0\n");
            setTextSeg("\tcontinue" + (continueCount++) + ": \n");
            break;

        case "!=":
            setTextSeg("\tc.eq.s $f0, $f2\n");
            setTextSeg("\tbc1t isEqualDouble" + labelCount + "\n");
            setTextSeg("\tj isNotEqualDouble" + continueCount + "\n");
            setTextSeg("\tisEqualDouble" + labelCount + ": \n");
            setTextSeg("\tli $t3 , 0\n");
            setTextSeg("\tj continue" + continueCount + "\n");
            setTextSeg("\tisNotEqualDouble" + (labelCount++) + ": \n");
            setTextSeg("\tli $t3 , 1\n");
            setTextSeg("\tcontinue" + (continueCount++) + ": \n");
            break;
    }

    setTextSeg("\n");
}
//------------------------------------------------------ boolean ----------------------------------------------------------------


    public static void loadConstantBoolCgen(Boolean boolVal){

        if (!dataSeg.contains("arthStackPtr"))
        {
            dataSeg += ("\tarthStackPtr: .word 268501344\n");
        }

        if (boolVal) {
            setTextSeg("\taddi $t3, $zero, 1\t\t\t\t# load costant bool(true).\n");
        }

        else {
            setTextSeg("\taddi $t3, $zero, 0\t\t\t\t# load costant bool(false).\n");
        }

        setTextSeg("\n");

    }

    public static void itobCgen(){

        if (!dataSeg.contains("arthStackPtr"))
        {
            dataSeg += ("\tarthStackPtr: .word 268501344\n");
        }

        setTextSeg("\tbeqz $t1, convertToFalse" + (converToFalseCount) +"\n" + "\t\taddi $t3, $zero, 1\n\t\tj continue"+ (continueCount) + "\n"
                + "\tconvertToFalse"+ (converToFalseCount++) + ":\n\t\tadd $t3, $zero, $zero" +
                "\ncontinue" + (continueCount++) + ":\n");

        setTextSeg("\n");
    }

    public static void booleanOprations(String opSem){

        switch (opSem){
            case "&&":

                setTextSeg("\tand $t3, $t1, $t2\t\t\t\t#expr and.\n");

                break;

            case "||":

                setTextSeg("\tor $t3, $t1, $t2\t\t\t\t#expr or.\n");

                break;

            case "!":

                setTextSeg("\taddi $t2, $zero, 0x00000001\n" +
                        "\tnor $t3, $t1, $t1\t\t\t\t#expr not.\n\tand $t3, $t3, $t2\n");
                break;
        }

        setTextSeg("\n");

    }

    //--------------------------------------- syscall functions ------------------------------------------------------

    public static void readInt(){

        dataSeg += "\tinputNum" + inputNumberCount + ": .asciiz \"\" \n ";
        setTextSeg( "\n\tla $t5, inputNum" + inputNumberCount +
                "\n" + "\tli $v0, 8\n" + "\tadd $a0, $t5, $zero\n"
                + "\n\tli $a1, 0xA\t\t\t\t#size of max int digits." + "\n\tsyscall\n");
        setTextSeg( "\tli $s1, 10\n" + "\tli $s2, 0\n" + "\tli $s3, 10\n" + "\treadInt" + readIntCount + ":");
        setTextSeg( "\n\tlb $v0, 0($t5)\n\tbeq $v0, $s1, opReadIntComplete" + opReadIntCompleteCount + "\n\tli $t4, 57");
        setTextSeg( "\n\tbgt $v0, $t4, badCharInput" + badCharInputCount +
                "\n\tli $t4, 48\n" + "\tblt $v0, $t4, badCharInput" + badCharInputCount);
        setTextSeg( "\n\tadd $t1, $zero, $v0\n\tsub $t1, $t1, 48\n\tmult $t3, $s3\n\tmflo $t4\n\tadd $t3, $t4, $t1");
        setTextSeg( "\n\taddi $t5, $t5, 1\n\taddi $s2, $s2, 1\n\tb readInt" + readIntCount);
        setTextSeg( "\n\tbadCharInput"+ badCharInputCount + ":" + "\n\tli $t3, 0" + "\n\topReadIntComplete" + opReadIntCompleteCount + ":\n");

        inputNumberCount++;    // maybe not need for reading we have multiple inputcounts..
        readIntCount++;
        opReadIntCompleteCount++;
        badCharInputCount++;

        setTextSeg("\n");
    }

    public static void readLine(){

        dataSeg += "\n\tinputString" + inputStringCount +":" + " .asciiz" + " \"\"";
        setTextSeg( "\n\tla $t5, inputString" + (inputStringCount++));
        setTextSeg( "\n\taddi $a0, $zero, $t5\n\tli $a1, 50\n\tsyscall");

        pushMips(5);  // for assignment.

        setTextSeg("\n");
    }

    public static void printNewLineCgen(){

        if(!dataSeg.contains("newLine: .asciiz \"\\n\"")){
            dataSeg += "\tnewLine: .asciiz \"\\n\"\n";
        }

        setTextSeg("\taddi $v0, $zero, 4\n" +
                "\tla $a0, newLine\n" +
                "\tsyscall\n");
    }

    public static void printString(){

        popMips(3);
        // lw t3, 0(t3)
        setTextSeg("\tadd $t2, $zero ,$t3\n" +
                "\tadd $a0, $zero ,$t2\n" +
                "\tli $v0, 4\n" +
                "\tsyscall\n");
    }

    //------------------------------------------- String ops ----------------------------------------------------------

    public static void CheckEqualCgen(){

        if (!getTextSeg().contains("la $t0 , 268501344")){
            setTextSeg("\tla $t0 , 268501344\n");
        }

        String lenStr = calculateLenStringcode();

        if (!functionList.contains(lenStr)) {
            functionList.add(calculateLenStringcode());
        }
        String checkEq = checkEqualCode();

        if (!functionList.contains(checkEq)){
            functionList.add(checkEqualCode());
        }

        popMips(3);

        setTextSeg(
                "\tadd $a0, $zero, $t3\t\t\t\t#load address of first in a0 \n" +
                        "\tjal calculateLenString \n" +
                        "\tadd $s3 , $zero,  $v1\n");

        popMips(4);

        setTextSeg(
                "\tadd $a0, $zero, $t4\t\t\t\t#load address of first in a0\n" +
                        "\tjal calculateLenString\n" +
                        "\tadd $s4 , $zero,  $v1");

        setTextSeg("\n\tbne $s3, $s4, notEqualStrs\n" +
                "\tadd $a0, $zero, $t3\n" +
                "\tadd $a1, $zero, $t4\n" +
                "\t\n" +
                "\tjal checkEqual\n" +
                "\tadd $t3, $v1, $zero\n" +
                "\tb endOfCompare\n" +
                "\t\n" +
                "\tnotEqualStrs:\n" +
                "\taddi $t3, $zero, 0\n" +
                "\n" +
                "\tendOfCompare:\n");
        //push(3)
        setTextSeg("\n");
    }

    public static void CheckNotEqualCgen(){

        CheckEqualCgen();
        notRegister(3);
        //push(3)
    }

    public static void notRegister(int regnum){

        setTextSeg("\tli $s1, 1\n" +
                "\tnot $t" + regnum + ", " + "$t" + regnum +"\n" +
                "\tand $t" + regnum + ", " +  "$t" + regnum + ", $s1\n");
        setTextSeg("\n");
    }

    public static String endOfProgramCode(){

        return "\tli $v0, 10\n" +
                "\tsyscall\n";

    }

    public static String calculateLenStringcode(){

        return "calculateLenString:" +
                "\n" +
                "\tli $s1, 0\n" +
                "\tlenCalculation:\n" +
                "\t\tlb $s0, 0($a0)\n" +
                "\t\tbeq $s0, 0, endLenCalculation\n" +
                "\t\tbeq $s0, '\\n', endLenCalculation\n" +
                "\t\taddi $s1, $s1, 1\n" +
                "\t\taddi $a0, $a0, 1\n" +
                "\t\tb lenCalculation\n" +
                "\t\t\n" +
                "\t\tendLenCalculation:\n" +
                "\tadd $v1, $zero, $s1\n" +
                "\t  jr $ra\n";
    }

    public static String checkEqualCode(){

        return "\ncheckEqual:\n" +
                "\t\n" +
                "\tcontentCompare:\n" +
                "\tlb $s0, 0($a0)\n" +
                "\tlb $s2, 0($a1)\n" +
                "\t\n" +
                "\tbne $s0, $s2, endContentCompare\n" +
                "\tbeq $s0, 0, endOfStrs\n" +
                "\tbeq $s0, '\\n', endOfStrs\n" +
                "\t\n" +
                "\tadd $a0, $a0, 1\n" +
                "\tadd $a1, $a1, 1\n" +
                "\t\n" +
                "\tb contentCompare\n" +
                "\t\n" +
                "\tendOfStrs:   # 2 strs are equal.\n" +
                "\t\n" +
                "\taddi $v1, $zero, 1\n" +
                "\tjr $ra\n" +
                "\t\n" +
                "\tendContentCompare:   #not equal.\n" +
                "\taddi $v1, $zero, 0\n" +
                "\tjr $ra\n";
    }

    public static void loadStringConstant(String strConstantValue){

        dataSeg +="\tstrConstant" + strConstantCount + ":" + " .asciiz " + strConstantValue + "\n";   // load string in memory in runtime
        setTextSeg( "\tla $t3, strConstant" + strConstantCount++ + "\n");    // and then we need to push address ($t3) in to the memory.

        setTextSeg("\n");
    }

    public static void declStringCgen(String ID){

        DSCP d = STSStack.stack.getDSCP(ID);
        d.setAddress(ID + stringRef);
        dataSeg += "\t" + d.getAddress() + ": .word" +  " 0\t\t\t\t#string reference decl.\n";
        stringRef++;

        setTextSeg("\n");


    }

    public static void stringAssignment(DSCP lval){

        //needs stack ??
        //setTextSeg( "\tla $t4, strConstant" + (strConstantCount++) + "\t\t\t\t#loading address of memory that contains string.\n";
        // setTextSeg( "\tli $t4, $t3"+ "\t\t\t\t#loading address of memory that contains string(from $t3).\n");
        setTextSeg( "\tla $t1, " + lval.getAddress() + "\n");
        // this line will copy the address of memory that have that string to another memory place for variable address holder.
        setTextSeg( "\tsw $t3, 0($t1)\t\t\t\t#now " + lval.getAddress() +  " have string address in memory." + "\n"); // popMips(3).

        pushMips(3);  // store address of reference variable.
        // for Print(a) // we must push $t3.. if we push t3, we will have address of string in memory. but if we push t1, we must lw after pop.

        setTextSeg("\n");

    }

    public static void stringConcatCgen(){

        popMips(2);   // right str
        popMips(1);  // left str

        setTextSeg( "\tadd $t4, $zero, $t1\n" + "\tli $s0, 0\t\t\t\t#len of string counter.\n");
        setTextSeg( "\tlenCalculation" + lenCalculationCount + ":\n"
                + "\tlb $s1, 0($t1)\n\tbeq $s1, 0, endLenCalculation"
                + endLenCalculationCount +
                "\n\tbeq $s1, '\\n', endLenCalculation" + lenCalculationCount + "\n" + "\taddi $s0, $s0, 1\n\taddi $t1, $t1, 1" + "\n\tb lenCalculation" + lenCalculationCount + "\n\tendLenCalculation" + endLenCalculationCount +":\n");
        pushMips(4);   // push the address of concat string.
        setTextSeg( "\tadd $t4, $s0, $t4\n");
        //popMips(6);
        //popMips(2);
        setTextSeg( "\tconcat" + concatCount + ":\n" + "\tlb $s2, 0($t2)\n\tsb $zero, 0($t2)\t\t\t\t#reset byte.\n\tbeq $s2, 0, endConcat" + endConcatCount + "\n\tbeq $s2, '\\n', endConcat"+ endConcatCount + "\n");
        setTextSeg( "\tsb $s2, 0($t4)\t\t\t\t#store second string byte values in to the following of first string.\n");
        setTextSeg( "\taddi $t2, $t2, 1\n" + "\taddi $t4, $t4, 1\n" + "\tb concat" + concatCount + "\n\tendConcat" + endConcatCount + ":\n");

        endConcatCount++;
        lenCalculationCount++;
        endLenCalculationCount++;
        concatCount++;

        setTextSeg("\n");
    }

//Loops
//------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------
//$t3 is the condition-check value , 1 true , 0 false

    public static void ifElseCgen()
    {
        ifIndexLabelCount++;
        int temp = ifLabelCount + ifIndexLabelCount;

        if(ifMaxLabelCount < temp)
            ifMaxLabelCount = temp;

        setTextSeg("If"+ temp + ": \t\t\t\t#if started.\n");
        setTextSeg("bne $t3 , 1 , Else" + temp + "\n");

        setTextSeg("\n");
    }
    //ifstatement
    public static void ifElseCgen_ElseSection()
    {
        int temp = ifLabelCount + ifIndexLabelCount;

        setTextSeg("j IfExit" + temp + "\n");
        setTextSeg("Else" + temp + ": \t\t\t\t#else started.\n");

        setTextSeg("\n");
    }
    //eslestate
    public static void ifElseCgen_Ending()
    {
        int temp = ifLabelCount + ifIndexLabelCount;

        setTextSeg("IfExit" + temp + ": \t\t\t\t#if ended.\n");

        ifIndexLabelCount--;

        if(ifIndexLabelCount == 0)
            ifLabelCount = ifMaxLabelCount;

        setTextSeg("\n");
    }

//---------------------------------------------------------------------------

    public static void breakCgen()
    {
        int temp = loopLabelCount + loopIndexLabelCount;
        setTextSeg("j exitLoop" + temp +"\t\t\t\t # break stmt\n");

        setTextSeg("\n");
    }

    public static void continueCgen()
    {
        int temp = loopLabelCount + loopIndexLabelCount;
        setTextSeg("j Loop" + temp +"\t\t\t\t # continue stmt\n");

        setTextSeg("\n");
    }

    public static void loopCgen()
    {
        loopIndexLabelCount++;
        int temp = loopLabelCount + loopIndexLabelCount;

        if(loopMaxLabelCount < temp)
            loopMaxLabelCount = temp;

        setTextSeg("Loop"+ temp + ": \n");

        setTextSeg("\n");

        System.out.println(FUEXP);
        fuexpStack.push(FUEXP);
        FUEXP = "";

    }

    public static void loopCondition()
    {
        int temp = loopLabelCount + loopIndexLabelCount;
        popMips(3);
        setTextSeg("bne $t3 , 1 , exitLoop" + temp + "\n");

        setTextSeg("\n");
    }

    public static void ForUpdateCgen(){

        setTextSeg(FUEXP);
        //FUEXP = "";
    }

    public static void loopCgen_Ending()
    {
        FUEXP = fuexpStack.pop();

        int temp = loopLabelCount + loopIndexLabelCount;

        setTextSeg("j Loop" + temp +"\n");
        setTextSeg(" exitLoop" + temp + ": \n");
        loopIndexLabelCount--;

        if(loopIndexLabelCount == 0)
            loopLabelCount = loopMaxLabelCount;

        setTextSeg("\n");
    }

    public static String getTextSeg(){
        if(inFUEXP)
            return FUEXP;
        else
            return textSeg;
    }

    public static void setTextSeg(String currentTextSeg){
        if(inFUEXP)
            FUEXP += currentTextSeg;
        else
            textSeg += currentTextSeg;
    }

    public static void toggleInFUEXP(){
        inFUEXP = !inFUEXP;
    }

public static void errorCgen(String input)
    {
        dataSeg += "semanticErr: .asciiz " + " \"" + input + "\"" + "\n";
        setTextSeg("la $a0 , semanticErr\n");
        setTextSeg("li $v0 , 4\n");
        setTextSeg("syscall\n");
    }

    public static void compile(String outputFile) throws IOException {
        FileWriter out = new FileWriter(outputFile);


        textSeg += endOfProgramCode();

        if (!functionList.isEmpty()) {

            textSeg += "\n#----------------- functions ---------------------------\n";

            for (String function : functionList)
                textSeg = textSeg + function;
        }

        //System.out.println(dataSeg + textSeg);
        out.write(dataSeg + textSeg + "\n");
        out.close();
    }


// public static void functionDecleration(String functionID, String fLabel, function parameters?){
//
// }

// public static void functionCall(ClassType ct){ // ## handlee
//
// }

}
