

public abstract class Automaton {

    public FiveTuple five_tuple;

    Automaton(FiveTuple ft) {
        this.five_tuple = ft;
    }

    public abstract void build();
}