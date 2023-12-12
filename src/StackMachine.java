/**
 * StackMachine class
 * builds a stack machine based on provided NFA 5-Tuple
 */
public class StackMachine {
    
    // member variables
    public FourTuple four_tuple;
    private FiveTuple nfa;

    /**
     * constructor for StackMachine
     * @param ft FourTuple to be populated
     * @param nfa FiveTuple from NFA to use for construction
     */
    public StackMachine(FourTuple ft, FiveTuple nfa) {
        this.four_tuple = ft;
        this.nfa = nfa;

        build();
    }

    /**
     * populates stack alphabet and delta table
     */
    public void build() {
        // populate stack symbols
        for(String state: nfa.getStates()) {
            four_tuple.addStackSymbol(state);
        }

        // populate delta functions (nfaDeltas --> stack machine)
        for(String[] transition : nfa.getDelta()) {
            four_tuple.addDelta(transition[1], transition[0], transition[2]);
        }
        System.out.println("Stack Machine created.");
    }
}
