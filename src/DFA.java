

public class DFA extends Automaton {

    private FiveTuple nfaTuple;

    DFA(FiveTuple dft, FiveTuple nft) {
        super(dft);
        this.nfaTuple = nft;
    }
    
    public void build() {

    }
}
