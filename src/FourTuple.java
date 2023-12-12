import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/***
 * FourTuple class
 * holds all data for Stack Machine
 */
public class FourTuple {
    
    // 4-Tuple atributes for stack machine
    private ArrayList<String> stackAlphabet = new ArrayList<String>();      // Gamma - stack alphabet
    private ArrayList<String> inputAlphabet = new ArrayList<String>();      // Sigma - input alphabet
    private String initial = "q0";                                          // initial stack symbol S
    private ArrayList<String[]> delta = new ArrayList<String[]>();          // delta transitions

    /**
     * constructor for FourTuple
     * @param inFile fine to read alphabet from
     */
    public FourTuple(String inFile) {
        readAlphabet(inFile);
    }

    /***
     * reads alphabet from file and updates alphabet member variable
     * @param inFile file to read alphabet from
     */
    private void readAlphabet(String inFile) {
        try {
            Scanner input = new Scanner(new File(inFile));

            // while there is still content to read from the file
            while(input.hasNextLine()) {
                String line = input.nextLine(); // get next line

                // for each character in the line
                for (int i = 0; i < line.length(); i++) {
                    char ch = line.charAt(i);
                    // check if character (special symbols not part of the alphabet)
                    if(Character.isDigit(ch) || Character.isAlphabetic(ch)) {
                        // convert to String
                        String c = Character.toString(ch);
                        // if not already in the alphabet, add the character
                        if(!this.inputAlphabet.contains(c)) {
                            this.inputAlphabet.add(c);
                        }
                    }
                }
            }
            // close file
            input.close();

        } catch (FileNotFoundException ex) {
            System.out.println("File not found.");
            ex.printStackTrace();
        }
    }

    /**
     * getter function for input alphabet
     * @return ArrayList<String> of input alphabet characters
     */
    public ArrayList<String> getInputAlphabet() {
        return this.inputAlphabet;
    }

    /**
     * getter function for stack alphabet symbols
     * @return ArrayList<String> of stack alphabet symbols
     */
    public ArrayList<String> getStackAlphabet() {
        return this.stackAlphabet;
    }

    /**
     * adder function for stack alphabet symbols
     * @param s
     */
    public void addStackSymbol(String s) {
        this.stackAlphabet.add(s);
    }

    /**
     * getter function for list of delta transition functions
     * @return ArrayList<String[]> of delta transition functions in the form String[read, pop, push]
     */
    public ArrayList<String[]> getDelta() {
        return this.delta;
    }

    /**
     * adder function for delta transitions
     * @param read String that is read
     * @param pop String that is popped off the stack
     * @param push String that is pushed to the stack
     */
    public void addDelta(String read, String pop, String push) {
        String[] transition = {read, pop, push};
        this.delta.add(transition);
    }

    /**
     * toString function for FourTuple
     * outputs formatted table of all stack moves
     */
    public String toString() {
        String output = "+-------+-------+-------+\n" + 
                        "| read\t| pop\t| push\t|\n" +
                        "+-------+-------+-------+\n";

        for(String[] t : this.delta) {
            output += "| " + t[0] + "\t| " + t[1] + "\t| " + t[2] + "\t|\n";
        }

        output += "+-------+-------+-------+\n";

        return output;
    }
}
