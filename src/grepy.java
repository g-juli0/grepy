import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class grepy {

    // static variable that holds file names and the regular expression
    static String[] call_data = new String[4];
    // 0 - NFA fileName
    // 1 - DFA fileName
    // 2 - regular expression
    // 3 - input fileName

    /**
     * verifies if all characters in the expression are legal
     * @param exp String expression to check
     * @return boolean true if no illegal symbols are found
     */
    public static boolean verifyRegex(String exp) {
        for(int i = 0; i < exp.length(); i++) {
            char ch = exp.charAt(i);
            
            // check digits, uppercase, and lowercase
            if(Character.isDigit(ch) || Character.isAlphabetic(ch)) {
                continue;
            // check special characters
            } else if (ch == '(' || ch == ')' || ch == '*' || ch == '+') {
                continue;
            // any character remaining is invalid
            } else {
                return false;
            }
        }
        // if loop iterates with no invalid character detection, return true
        return true;
    }

    /**
     * verifies that the provided file name is an existing file
     * @param fileName String name of file to check
     * @return true if file exists
     */
    public static boolean verifyFile(String fileName) {
        File testFile = new File(fileName);
        return testFile.exists();
    }

    /**
     * tests each string of symbols in the input file against the DFA for language membership
     * @param fileName String name of input file
     * @param tuple FiveTuple to check strings against (NFA or DFA)
     */
    public static void processTests(String fileName, FiveTuple tuple) {
        try {
            FileInputStream fileInput = new FileInputStream(fileName);
            Scanner input = new Scanner(fileInput);

            while (input.hasNext()) {
                // set starting state and read next line of symbols
                String state = tuple.getStart();
                String line = input.nextLine();
                
                // check each character against each delta state
                for (int i = 0; i < line.length(); i++) {
                    for (int j = 0; j < tuple.getDelta().size(); j++) {
                        String[] temp = tuple.getDelta().get(j);
                        
                        // if start state and input character match, 
                        if (temp[0].equals(state) && temp[1].equals(Character.toString(line.charAt(i)))) {
                            state = temp[2]; // make transition, update current state
                            break;
                        }
                    }
                }

                // check state after all possible transitions are made
                for (int i = 0; i < tuple.getAcceptStates().size(); i++) {
                    if (state.equals(tuple.getAcceptStates().get(i))) {
                        System.out.println("Accepted: " + line);
                    }
                }
            }
            // extra line padding for output
            System.out.println();
            input.close();
        } catch (FileNotFoundException ex) {
            System.err.println("File not found.");
            ex.printStackTrace();
        }
    }

    /**
     * converts digraph in txt file to a .dot file
     * @param fileName String name of file to write NFA to
     * @param automaton the type of automaton to be converted
     * @param tuple the FiveTuple to be written in the graph
     */
    public static void convertToDOT(String fileName, String automaton, FiveTuple tuple) {

        // give default file name if none specified
        if (fileName == null || fileName.equals("")) {
            fileName = automaton + "Graph.dot";
        } else {
            // if provided a file name, check extension type
            int lastIndex = fileName.lastIndexOf(".");

            // no extension
            if (lastIndex == -1) {
                fileName = fileName + ".dot";   
            } else {
                // replace extension
                String sub = fileName.substring(lastIndex+1);

                if (!(sub.equals(".dot"))) {
                    fileName = fileName.substring(0, lastIndex) + ".dot";
                } 
            } 
        }

        try { // create or overwrite file to write to
            File f = new File(fileName);

            if (f.createNewFile()) {
                System.out.println("File " + fileName + " created");
            } else {
                System.out.println("File " + fileName + " already exists. Overwritten.");
            }

            FileWriter writer = new FileWriter(f);

            writer.write("digraph "
                + automaton + " {" 
                + System.getProperty( "line.separator" )
                + "\trankdir=LR"
                + System.getProperty( "line.separator"));
            
            // define each state with shape and name
            for(String state : tuple.getStates()) {
                if(!(tuple.getAcceptStates().contains(state))) {
                    writer.write("\t" + state + " [shape=circle] " + state + ";" + System.getProperty( "line.separator" ));
                }
            }

            // define each accepting state
            for (String accept : tuple.getAcceptStates()) {
                writer.write("\t" + accept + " [shape=doublecircle] " + accept + ";" + System.getProperty( "line.separator" ));
            }

            // define all transitions with labels
            for (String[] trans : tuple.getDelta()) {
                writer.write("\t" + trans[0] + " -> " + trans[2] + " [label=" + trans[1] + "];");
                writer.write(System.getProperty( "line.separator" ));
            }

            writer.write("}");
            writer.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * convers .dot file to png
     * @param dotFile String file name of .dot file to be converted
     */
    public static void convertToPNG(String dotFile) {
        try {
            File f = new File(dotFile); 
            String arg1 = f.getAbsolutePath(); // get full file path
            String arg2 = arg1.substring(0, arg1.indexOf(".")) + ".png"; 
            // define command
            String[] c = {"java", "-version", "dot", "-Tpng", arg1, "-o", arg2};
            Process p = Runtime.getRuntime().exec(c); 
            int err = p.waitFor(); // run and wait foe execution

            System.out.println("PNG created.");
        }
        catch(IOException ex1) {
            ex1.printStackTrace();
        }
        catch(InterruptedException ex2) {
            ex2.printStackTrace();
        }
    }

    /**
     * main function
     * @param args String[] input arguments at runtime
     * @throws Exception catches general exeptions
     */
    public static void main(String[] args) throws Exception {
        // args = [-n, NFAfile, -d, DFAfile, regex, inputFile, -v]
        //      -v is additional output flag (png)

        try {
            // check for minimum amount of arguments for computation
            if(args.length < 2) {
                throw new IllegalArgumentException("Not enough vaild arguments. Enter a regular expression and an input file.");
            }

            // flags for input checking
            String input = Arrays.toString(args);
            boolean nfaFlag = false;
            boolean dfaFlag = false;

            // check each input flag and load call_data
            // check for NFA (optional)
            if(input.contains("-n")) {
                call_data[0] = args[1];
                nfaFlag = true;
            }

            // check for DFA (optional)
            if(input.contains("-d")) {
                call_data[1] = args[3];
                dfaFlag = true;
            }

            // check for regular expression and input fileName
            if(nfaFlag && dfaFlag) {                            // both NFA and DFA files provided
                call_data[2] = args[4]; // regex
                call_data[3] = args[5]; // filename
            } else if(nfaFlag ^ dfaFlag) {                      // either NFA or DFA file provided (not both)
                call_data[2] = args[2]; // regex
                call_data[3] = args[3]; // filename
            } else {                                            // no NFA or DFA files provided
                call_data[2] = args[0]; // regex
                call_data[3] = args[1]; // filename
            }
            
            // verify accepted characters, otherwise throw exception
            if(!verifyRegex(call_data[2])) {
                throw new IllegalArgumentException("Regular expression contains invalid characters.");
            }

            // verify file exists, otherwilse throw exception
            if(!verifyFile(call_data[3])) {
                throw new FileNotFoundException("Cannot locate input file.");
            }

            // create NFA
            NFA nfa = new NFA(new FiveTuple(call_data[3]), call_data[2]);
            System.out.println(nfa.five_tuple.toString());

            // create DFA
            DFA dfa = new DFA(new FiveTuple(call_data[3]), nfa.getFiveTuple());
            System.out.println(dfa.five_tuple.toString());

            // create Stack Machine
            StackMachine sm = new StackMachine(new FourTuple(call_data[3]), nfa.five_tuple);
            System.out.println(sm.four_tuple.toString());

            // test each line of file against NFA/DFA/Stack machine
            processTests(call_data[3], dfa.five_tuple);

            // output NFA/DFA/Stack machine in .dot format
            convertToDOT(call_data[0], "NFA", nfa.five_tuple);
            convertToDOT(call_data[1], "DFA", dfa.five_tuple);

            // bonus - graphviz to convert .dot to .png
            if(input.contains("-v")) {
                convertToPNG("nfa.dot");
                convertToPNG("dfa.dot");
            }

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
}
