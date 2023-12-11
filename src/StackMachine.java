public class StackMachine {
    
    public FourTuple four_tuple;
    private FiveTuple nfa;

    public StackMachine(FourTuple ft, FiveTuple nfa) {
        this.four_tuple = ft;
        this.nfa = nfa;

        build();
    }

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

    public void helper() {

    }
}
