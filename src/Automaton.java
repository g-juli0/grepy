/**
 * Automaton class
 * parent class of NFA and DFA
 * requires a FiveTuple object and a build() function
 */
public abstract class Automaton {

    public FiveTuple five_tuple;

    Automaton(FiveTuple ft) {
        this.five_tuple = ft;
    }

    public abstract void build();

}