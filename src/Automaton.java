

public abstract class Automaton {

    public FiveTuple five_tuple;

    Automaton(FiveTuple ft) {
        this.five_tuple = ft;
    }

    public abstract void build();

    public boolean isValidChar(char ch) {
        // check digits, uppercase, and lowercase
        if(Character.isDigit(ch) || Character.isAlphabetic(ch)) {
            return true;
        // check special characters
        } else if (ch == '(' || ch == ')' || ch == '*' || ch == '+') {
            return true;
        // any character remaining is invalid
        } else {
            return false;
        }
    }
}