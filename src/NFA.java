

public class NFA extends Automaton {

    public String expression;
    public int currentState;

    NFA(FiveTuple ft, String expr) {
        super(ft);
        this.expression = expr;
        this.currentState = 0;

        build();
    }

    public FiveTuple getFiveTuple() {
        return this.five_tuple;
    }
    
    public void build() {
        String looperExp = expression;

        while(looperExp.length() > 0) {

            System.out.println(looperExp);

            if(looperExp.length() >= 2) {
                // process expression
                char current = looperExp.charAt(0);
                char next = looperExp.charAt(1);

                // determine if valid character and process
                if(this.isValidChar(current) && this.isValidChar(next)) {
                    // if current is character, process
                    if(Character.isAlphabetic(current) || Character.isDigit(current)) {
                        processChar(current, next);
                    } else { // if current is special, process
                        processSpecial(current, next);
                    }
                } else {
                    throw new IllegalArgumentException("Illegal character detected.");
                }

                looperExp = looperExp.substring(1);

            } else if (looperExp.length() == 1) {
                // process single char expression
                char current = looperExp.charAt(0);

                if(this.isValidChar(current)) {
                    if(Character.isAlphabetic(current) || Character.isDigit(current)) {
                        // add state
                        addState();
                        // add delta
                    } else { // if current is special, process
                        processSpecial(current);
                    }
                } else {
                    throw new IllegalArgumentException("Illegal character detected.");
                }

                looperExp = "";
                
                // update accept states
                addAcceptState(this.currentState);
                System.out.println("NFA Created");
            }
        }
        System.out.println(this.five_tuple.toString());

    }

    private void processChar(char cur, char nxt) {
        //System.out.println(cur);
    }

    private void processChar(char cur) {
        //
    }

    private void processSpecial(char cur, char nxt) {
        //System.out.println(cur);
    }

    private void processSpecial(char cur) {
        //System.out.println(cur);
    }

    public void addState() {
        this.five_tuple.addState();
    }

    public void addAcceptState(int state) {
        this.five_tuple.addAcceptState(state);
    }
}
