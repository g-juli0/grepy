

public abstract class Automaton {

    public FiveTuple five_tuple;

    Automaton(FiveTuple ft) {
        this.five_tuple = ft;
    }

    public abstract void build();

    public boolean isValidChar(char ch) {
        // check digits, uppercase, lowercase, and accpted special characters
        return (Character.isDigit(ch) || Character.isAlphabetic(ch) || ch == '(' || ch == ')' || ch == '*' || ch == '+');
    }
}