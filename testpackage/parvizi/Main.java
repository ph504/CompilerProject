import Model.CodeGen;
import Model.DecafScanner;
import Model.multipass.IPass;
import Model.parser;

import Model.typeSystem.SemanticErrorException;

import java.io.*;

public class Main {

    public static void main(String[] args) throws Exception {
        try {

            //File tests = new File("src\\testCases");
            //for (File inputD:tests.listFiles()) {


                String inputFile = null;// = "src\\testCases\\" + inputD.getName();
                String outputFile = null;
                if (args != null) {
                    for (int i = 0; i < args.length; i++) {
                        if (args[i].equals("-i")) {
                            inputFile = args[i + 1];
                        }
                        if (args[i].equals("-o")) {
                            outputFile = args[i + 1];
                        }
                    }
                }
                File read1 = null;
                File read2 = null;
                File read3 = null;
                if (inputFile != null) {
                    read1 = new File(inputFile);
                    read2 = new File(inputFile);
                    read3 = new File(inputFile);
                } else {
                    read1 = new File("testpackage/parvizi/Model/TestCases.txt");
                    read2 = new File("testpackage/parvizi/Model/TestCases.txt");
                    read3 = new File("testpackage/parvizi/Model/TestCases.txt");
                }

                if (outputFile == null)
                    outputFile = "out.asm";
                //System.out.println(read1);
                FileReader f1 = new FileReader(read1);
                FileReader f2 = new FileReader(read2);
                FileReader f3 = new FileReader(read3);

                try {
                    // todo
                    // first pass.
                    DecafScanner scanner = new DecafScanner(f1);
                    parser decafParser = new parser(scanner);
                    //IPass.pass = new FirstPass();
                    IPass.pass = "first";
                    decafParser.parse();

                    // second pass.
                    DecafScanner scanner2 = new DecafScanner(f2);
                    parser decafparser2 = new parser(scanner2);
                    //IPass.pass = new SecondPass();
                    IPass.pass = "second";
                    decafparser2.parse();

                    // third pass.
                    DecafScanner scanner3 = new DecafScanner(f3);
                    parser decafparser3 = new parser(scanner3);
                    //IPass.pass = new ThirdPass();
                    IPass.pass = "third";
                    decafparser3.parse();
                    //String outASM = inputD.getName().substring(0,inputD.getName().length()-1);
                    //System.out.println("OK");
                    //File f = new File("src\\outputs\\" + outASM + ".asm");
                    /*if(!f.exists())
                        f.createNewFile();*/
                    //System.out.println(f.getName());
                    CodeGen.compile(outputFile/*f.getName()*/);

                }catch (SemanticErrorException e){
                CodeGen.errorCgen("Semantic Error");
		//System.out.println("Semantic Error");
                CodeGen.compile(outputFile);
            }
            catch (Exception e){
                CodeGen.errorCgen("Syntax Error");
		//System.out.println("Syntax Error");
                CodeGen.compile(outputFile);
            }

            
        } catch (Exception e) {
            e.printStackTrace();
            Writer writer;
            String outputFile = null;
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    if (args[i].equals("-o")) {
                        outputFile = args[i + 1];
                    }
                }
            }
            if (outputFile != null) {
                writer = new FileWriter("out/" + outputFile);
            } else {
                writer = new OutputStreamWriter(System.out);
            }

            writer.write("NO");
            writer.flush();
            writer.close();
        }

        // the old Main.
        /*
        File file = new File("src\\Model\\TestCases.txt");
        File file2 = new File("src\\Model\\TestCases.txt");
        File file3 = new File("src\\Model\\TestCases.txt");
        FileReader f1 = new FileReader(file);
        FileReader f2 = new FileReader(file2);
        FileReader f3 = new FileReader(file3);

        DecafScanner scanner = new DecafScanner(f1);
        parser decafParser = new parser(scanner);
        //IPass.pass = new FirstPass();
        IPass.pass = "first";
        decafParser.parse();

        // first pass
        DecafScanner scanner2 = new DecafScanner(f2);
        parser decafparser2 = new parser(scanner2);
        //IPass.pass = new SecondPass();
        IPass.pass = "second";
        decafparser2.parse();

        DecafScanner scanner3 = new DecafScanner(f2);
        parser decafparser3 = new parser(scanner2);
        //IPass.pass = new ThirdPass();
        IPass.pass = "third";
        decafparser3.parse();*/

        //System.out.println("assembly generated !\n");


    }

}
