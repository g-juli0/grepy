import java.util.ArrayList;

public class FiveTuple {
    
    // 5-Tuple attributes for DFA and NFA
    private ArrayList<String> states = new ArrayList<String>();             // Q        (set of all states)
    private ArrayList<String> alphabet = new ArrayList<String>();           // Sigma    (input alphabet)
    private ArrayList<String[]> delta = new ArrayList<String[]>();          // delta    (transition function)
    private String start = new String();                                    // q0       (start state)
    private ArrayList<String> acceptingStates = new ArrayList<String>();    // F        (accepting states)

    public FiveTuple(String inFile) {
        this.states.add("q0");
        this.start = "q0";

        readAlphabet();
    }

    private void readAlphabet() {
        
    }
}
