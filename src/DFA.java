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
    }
    
    public void build() {

    }

    public void helper() {
        
    }
}
