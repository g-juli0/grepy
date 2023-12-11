import java.util.ArrayList;
import java.util.Arrays;

/**
 * DFA class
 * builds DFA FiveTuple based on provided NFA's Fivetuple
 */
public class DFA extends Automaton {

    private FiveTuple nfaTuple;

    private String currentState;
    private String errorState;
    private String currentChar;

    DFA(FiveTuple dft, FiveTuple nft) {
        super(dft);
        this.nfaTuple = nft;

        build();
    }
    
    public void build() {
        int index = 0;

        while (index <= this.five_tuple.getStates().size() - 1) {
            currentState = this.five_tuple.getStates().get(index);                               // Assign CurState

            for (int i = 0; i < this.five_tuple.getAlphabet().size(); i++) {                 // Iterate over Alphabet
                currentChar = this.five_tuple.getAlphabet().get(i);
                helper();
            }

            index++;
        }
        addState(errorState); // Define Error State in Tuple
        createErrorState();                                                                  
        addAcceptState();  // Add Accepting States

        System.out.println("DFA created");
    }

    public void helper() {
        ArrayList<String[]> transitions = getNewDeltas(); 

        String nxtState = getNextState(transitions);

        if (nxtState.equals("")) {                                                      // Error State Transition
            addDelta(currentState, currentChar, errorState);                                    

        } else {
            if (this.five_tuple.getStates().contains(nxtState)) {                            // Create Transition
                addDelta(currentState, currentChar, nxtState);

            } else {                                                                    // Create New State + Transition
                addState(nxtState);                                
                addDelta(currentState, currentChar, nxtState);
            } 
        } 
    }

    public ArrayList<String[]> getNewDeltas() {
        ArrayList<String[]> transitions = new ArrayList<String[]>();          // Record all Matched Transitions

        // Breakdown CurState to find NFA States
        ArrayList<String> currentStates = getStates();

        System.out.println(currentStates);

        for (int i = 0; i < currentStates.size(); i++) {
            String[] trans = {currentStates.get(i), "", ""};                 // Current NFA State

            // Find Delta Transitions matching our Current NFA State and CurChar
            for (int j = 0; j < this.nfaTuple.getDelta().size(); j++) {
                String[] temp = this.nfaTuple.getDelta().get(j);
                
                if (temp[0].equals(trans[0]) && temp[1].equals(currentChar)) {
                    this.five_tuple.addDelta(temp[0], currentChar, temp[2]);      
                }
            }

            // Add Trans Object to our Transitions
            transitions.add(trans);
        }
        return transitions;
    }

    public ArrayList<String> getStates() {
        ArrayList<String> curStates = new ArrayList<String>();                          // List for States
        String states = currentState;
        System.out.println(states);

        while (states.length() > 1) {
            for (int i = 1; i < states.length(); i++) {
                if (String.valueOf(states.charAt(i-1)).equals("q")) {                        //Marks Next State
                    curStates.add(states.substring(0, i));
                    states = states.substring(i); 
                    break;
                } else if (i == states.length() - 1) {                                  // Last State
                    curStates.add(states);
                    states = " ";
                    break;
                }
            }
        }
        return curStates;
    }

    public String getNextState(ArrayList<String[]> deltas) {
        String nxt = "";

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

    public void createErrorState() {
        // create delta transitions
        for (int i = 0; i < this.five_tuple.getAlphabet().size(); i++) {   
            String temp = this.five_tuple.getAlphabet().get(i);
            addDelta(errorState, temp, errorState);// Create Loop per Char
        }
    }

    public void addAcceptState() {
        ArrayList<String> accepting = this.nfaTuple.getAcceptStates();

        for (String state : this.five_tuple.getStates()) {
            for (String accept : accepting) {
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

    public void addDelta(String startState, String ch, String endState) {
        this.five_tuple.addDelta(startState, ch, endState);
    }

    public void addState(String state) {
        this.five_tuple.addState(state);
    }
}
