import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/***
 * FiveTuple class
 * holds all data for NFA and DFA Automata
 */
public class FiveTuple {
    
    // 5-Tuple attributes for DFA and NFA
    private ArrayList<String> states = new ArrayList<String>();             // Q        (set of all states)
    private ArrayList<String> alphabet = new ArrayList<String>();           // Sigma    (input alphabet)
    private ArrayList<String[]> delta = new ArrayList<String[]>();          // delta    (transition function)
    private String start =  "";                                             // q0       (start state)
    private ArrayList<String> acceptStates = new ArrayList<String>();       // F        (accepting states)

    /***
     * constructor for FiveTuple
     * @param inFile file to read alphabet from
     */
    public FiveTuple(String inFile) {
        this.states.add("q0");
        this.start = "q0";

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
                        if(!this.alphabet.contains(c)) {
                            this.alphabet.add(c);
                        }
                    }
                }
            }
            // close file
            input.close();

        } catch (FileNotFoundException ex) {
            System.err.println("File not found.");
            ex.printStackTrace();
        }
    }

    /***
     * getter function for list of states
     * @return ArrayList<String> of states
     */
    public ArrayList<String> getStates() {
        return this.states;
    }

    /***
     * adder function for list of states
     */
    public void addState(String state) {
        this.getStates().add(state);
    }

    /***
     * getter function for list of alphabet characters
     * @return ArrayList<String> of aplhabet characters
     */
    public ArrayList<String> getAlphabet() {
        return this.alphabet;
    }

    /***
     * getter function for list of delta transition functions
     * @return ArrayList<String> of delta transition functions in the form String[startState, inputSymbol, nextState]
     */
    public ArrayList<String[]> getDelta() {
        return this.delta;
    }

    /***
     * adder function for delta transition function
     * @param startState int state that transition starts from
     * @param inputSymbol String input symbol
     * @param nextState int next state after processing input symbol
     */
    public void addDelta(String startState, String inputSymbol, String nextState) {
        String[] transition = new String[] {startState, inputSymbol, nextState};
        this.getDelta().add(transition);
    }

    /***
     * getter function for start state
     * @return String start state
     */
    public String getStart() {
        return this.start;
    }

    /***
     * getter function for list of accepting states
     * @return ArrayList<String> of all accepting states
     */
    public ArrayList<String> getAcceptStates() {
        return this.acceptStates;
    }

    /***
     * adder function for list of accepting states
     * @param i index of existing state to be added to list of accepting states
     */
    public void addAcceptState(int i) {
        String newAcceptState = this.getStates().get(i);
        this.getAcceptStates().add(newAcceptState);
    }

    /***
     * toString function for FiveTuple object
     * outputs formatted line for each element of the FiveTuple
     */
    public String toString() {
        String output = "";

        output += "States: " + this.getStates().toString() + "\n";
        output += "Alphabet: " + this.getAlphabet().toString() + "\n";

        output += "Delta:";
        for(String[] transition : this.getDelta()){
            output += "\t" + Arrays.toString(transition) + "\n";
        }
        
        output += "Start State: " + this.getStart() + "\n";
        output += "Accept States: " + this.getAcceptStates().toString() + "\n";

        return output;
    }
}
