import java.util.ArrayList;
import java.util.Arrays;

/**
 * DFA class
 * builds DFA FiveTuple based on provided NFA's Fivetuple
 */
public class DFA extends Automaton {

    // member variables
    private FiveTuple nfaTuple;
    private String currentState;
    private String errorState;
    private String currentChar;

    /**
     * constructor for DFA object
     * @param dft
     * @param nft
     */
    DFA(FiveTuple dft, FiveTuple nft) {
        super(dft);
        this.nfaTuple = nft;

        build();
    }
    
    /**
     * populates DFA FiveTuple object based on provided NFA FiveTuple
     */
    public void build() {
        int index = 0;

        while (index <= this.five_tuple.getStates().size() - 1) {
            // assign currentState
            currentState = this.five_tuple.getStates().get(index);

            // iterate through alphabet and get transitions
            for (int i = 0; i < this.five_tuple.getAlphabet().size(); i++) {
                currentChar = this.five_tuple.getAlphabet().get(i);
                helper();
            }
            index++;
        }
        // define error state
        addState(errorState);
        createErrorState();                 
        // add accept state(s)                                                 
        addAcceptState();

        // print confirmation message
        System.out.println("DFA created.");
    }

    /**
     * helper function for DFA creation
     * gets nfa transitions and next state and creates dfa transitions
     */
    public void helper() {
        ArrayList<String[]> transitions = getNewDeltas(); 
        String nxtState = getNextState(transitions);

        // transition to error state
        if (nxtState.equals("")) {
            addDelta(currentState, currentChar, errorState);                                    

        } else {
            // add transition
            if (this.five_tuple.getStates().contains(nxtState)) {
                addDelta(currentState, currentChar, nxtState);

            // create new state and add transition
            } else {
                addState(nxtState);                                
                addDelta(currentState, currentChar, nxtState);
            } 
        } 
    }

    /**
     * returns list of delta transitions matched by state and character
     * @return ArrayList<String[]> of nfa matched deltas
     */
    public ArrayList<String[]> getNewDeltas() {
        // list of all matched transitions
        ArrayList<String[]> transitions = new ArrayList<String[]>();

        // Breakdown CurState to find NFA States
        ArrayList<String> currentStates = getStates();

        for (int i = 0; i < currentStates.size(); i++) {
            String[] trans = {currentStates.get(i), "", ""}; // Current NFA State

            // Find Delta Transitions matching our Current NFA State and CurChar
            for (int j = 0; j < this.nfaTuple.getDelta().size(); j++) {
                String[] temp = this.nfaTuple.getDelta().get(j);
                
                // if start states and characters match
                if (temp[0].equals(trans[0]) && temp[1].equals(currentChar)) {
                    this.five_tuple.addDelta(temp[0], currentChar, temp[2]);      
                }
            }

            // add transition to list of matched transitions
            transitions.add(trans);
        }
        return transitions;
    }

    /**
     * returns states converted from nfa to dfa
     * @return ArrayList<String> of dfa states
     */
    public ArrayList<String> getStates() {
        ArrayList<String> curStates = new ArrayList<String>();
        String states = currentState;

        while (states.length() > 1) {
            for (int i = 1; i < states.length(); i++) {
                // next state
                if (String.valueOf(states.charAt(i-1)).equals("q")) { 
                    curStates.add(states.substring(0, i));
                    states = states.substring(i); 
                    break;
                // last state
                } else if (i == states.length() - 1) {
                    curStates.add(states);
                    states = " ";
                    break;
                }
            }
        }
        return curStates;
    }

    /**
     * gets next state based on nfa states and transitions
     * @param deltas ArrayList<String[]> of transitions
     * @return String of next state
     */
    public String getNextState(ArrayList<String[]> deltas) {
        String nxt = "";

        // compare each transition for start state and input symbol match
        for (int i = 0; i < deltas.size()-1; i++) {
        	String start1 = deltas.get(i)[0];
        	String char1 = deltas.get(i)[1];
        	String end1 = deltas.get(i)[2];
            for (int j = 1; j < deltas.size(); j++) {
            	String start2 = deltas.get(j)[0];
            	String char2 = deltas.get(j)[1];
            	String end2 = deltas.get(j)[2];
                if (start1.equals(start2) && char1.equals(char2) && !end1.equals(end2)) {
                    nxt += end1 + end2; // add new state
                }
            }
        }

        // if no next state is found, create an error state
        if (nxt.equals("") && errorState == null) {   
            errorState = "q" + ((this.nfaTuple.getStates()).size()-1);                                                       
        }
        return nxt;
    }

    /**
     * creates a new state using the error state
     */
    public void createErrorState() {
        // create delta transitions
        for (int i = 0; i < this.five_tuple.getAlphabet().size(); i++) {   
            String temp = this.five_tuple.getAlphabet().get(i);
            // create loopback to errorState for character
            addDelta(errorState, temp, errorState);
        }
    }

    /**
     * loops through each DFA state and if any part of the state contains
     * an NFA accept state, add it to DFA accepting state list
     */
    public void addAcceptState() {
        ArrayList<String> accepting = this.nfaTuple.getAcceptStates();

        for (String state : this.five_tuple.getStates()) {
            for (String accept : accepting) {
                // if already marked, continue loop
                if (this.five_tuple.getAcceptStates().contains(state)) {
                    break;                                          
                } else {
                    if (state.contains(accept)) {
                        // add accept state
                        this.five_tuple.addAcceptState(this.five_tuple.getStates().indexOf(state));
                    }
                }
            }
        }
    }

    /**
     * adder function for delta transitions
     * @param startState String state that transition starts in
     * @param ch String character input
     * @param endState String state that transition ends in
     */
    public void addDelta(String startState, String ch, String endState) {
        this.five_tuple.addDelta(startState, ch, endState);
    }

    /**
     * adder function for list of states
     * @param state
     */
    public void addState(String state) {
        this.five_tuple.addState(state);
    }
}
