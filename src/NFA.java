/**
 * NFA class
 * builds NFA five-tuple based on provided regex
 */
public class NFA extends Automaton {

    // member variables
    private String expression;
    private String prevChar;
    private int currentState;
    private int prevState;
    private int beforeState;

    private int loopIndex;
    private boolean loopFlag;

    private String acceptMsg = "NFA created";
    private String rejectMsg = "Error: invalid character";

    // constructor
    NFA(FiveTuple ft, String expr) {
        super(ft);
        this.expression = expr;
        this.currentState = 0;

        build();
    }

    /**
     * getter for NFA FiveTuple object
     * @return FiveTuple
     */
    public FiveTuple getFiveTuple() {
        return this.five_tuple;
    }
    
    /**
     * executes recursive case for NFA FiveTuple building
     */
    public void build() {
        char current = this.expression.charAt(0); 	// get current char
        char next = this.expression.charAt(1); 		// get next char

        if(Character.isDigit(current) || Character.isAlphabetic(current)) {
        	processChar(current, next); // process character
        } else if (current == '(' || current == ')' || current == '*' || current == '+') {
        	processSpecial(current, next); // process special char
        } else { // error
            System.out.println(rejectMsg);
        }
    }

    /**
     * helper function for recursive build() function
     * @param updated String expression, updated as each character is processed
     * @param prev char previous character in the expression
     */
    public void helper(String updated, char prev) {
        this.expression = updated; // update expression member variable

        if (this.expression.length() >= 2) { // recursive case
            build();
        } else if (this.expression.length() == 1) { // base case
            char cur = this.expression.charAt(0);

            if(Character.isDigit(cur) || Character.isAlphabetic(cur)) {
                // if character, add new state and corresponding transition
                addState();
                addDelta(this.prevState, Character.toString(cur), this.currentState);
            } else if (cur == '(' || cur == ')' || cur == '*' || cur == '+') {
                // process special with 'B' blank character - end of expression
            	processSpecial(cur, 'B');
            } else { // error
                System.out.println(rejectMsg);
            }

            // update accepting states
            addAcceptState(this.currentState);
            System.out.println(acceptMsg);

        } else {
            // update accepting states
            addAcceptState(this.currentState);
            System.out.println(acceptMsg);
        }
    }

    /**
     * processes regular characters, adding states and transitions
     * @param cur char current character in expression being processed
     * @param nxt char next character (lookahead character)
     */
    private void processChar(char cur, char nxt) {
        // set flags for processing
        boolean openParen = false;
        int index = 1;

        if(Character.isDigit(nxt) || Character.isAlphabetic(nxt)) {
            // if character, add new state and corresponding transition
        	addState();
            addDelta(this.prevState, Character.toString(cur), this.currentState);
        } else if (nxt == '(' || nxt == ')' || nxt == '*' || nxt == '+') {
            // cycle through possible special characters
            if (nxt == '(') {
		    	openParen = true; // open paren detected
		
                // add new state and transition
		        addState();
		        addDelta(this.prevState, Character.toString(cur), this.currentState);
		
                // save current state
		        this.beforeState = this.currentState;
            }
            if (nxt == '+') {
                this.prevChar = Character.toString(cur);
            }
            // detects loop
            if ((nxt == '*') && this.expression.indexOf("*") == this.loopIndex) {
                // adds transition and moves state
                addDelta(this.currentState, Character.toString(cur), this.beforeState);
                this.currentState = this.beforeState;

                // reset flags and increment index
                index++;
                this.loopFlag = false; 
                openParen = false;
            }
            // not looping
            else {
                addDelta(this.currentState, Character.toString(cur), this.currentState);
                index = index + 1;
            }
        } else {
                System.out.println(rejectMsg);
        }

        // update loop index
        if (this.loopFlag) {
            this.loopIndex -= index;                                   
        }  

        // update expression after processing
        if (openParen) {
            String newExp = updateExpression();
            helper(newExp, cur);
        } else {
            String sub = this.expression.substring(index, this.expression.length());
            helper(sub, cur);
        }
    }

    /**
     * processes special characters, setting flags and incrementing loop index
     * @param cur char current character being processed
     * @param nxt char lookahead character
     */
    private void processSpecial(char cur, char nxt) {
        // initialize flags
        boolean openParen = false;
        int index = 1;

        switch(cur) {
            case '+':
                // add delta for previous character and next character
                addState();
                addDelta(this.prevState, this.prevChar, this.currentState);
                addDelta(this.prevState, Character.toString(nxt), this.currentState);
                index++; // ship next character (Ex: a OR b - dont need to process b)
                break;

            case '(':
                // save current state
                openParen = true;
                this.beforeState = this.currentState;
                break;
            
            case 'B':
                // last character placeholder
                break;

            default:
                System.out.println(rejectMsg);
                break;
        }

        // update loop index
        if (this.loopFlag) {
            this.loopIndex -= index;
        }                                      

        // update expression after processing
        if (openParen) {
            String newExp = updateExpression();
            helper(newExp, cur);
        } else {
            String sub = this.expression.substring(index, this.expression.length());
            helper(sub, cur);
        }
    }

    /**
     * add function for NFA
     * increments state and adds state to FiveTuple object
     */
    public void addState() {
        this.prevState = this.currentState++;
        this.five_tuple.addState();
    }

    /**
     * add function for new state
     * @param state int number of state to add (required by FiveTuple obj addAcceptState() function)
     */
    public void addAcceptState(int state) {
        this.five_tuple.addAcceptState(state);
    }

    /**
     * adds delta transition to FiveTuple array
     * @param startState int state transition begins at
     * @param ch char character processed
     * @param nextState int state transition ends at
     */
    public void addDelta(int startState, String ch, int nextState) {
        this.five_tuple.addDelta(startState, ch, nextState);
    }

    /**
     * loops through expression to update member variable
     * @return updated expression substring
     */
    public String updateExpression() {
        String substring = "";
        out:
        for (int i = 0; i < this.expression.length(); i++) {
            if (this.expression.charAt(i) == '(') {
                for (int j = i; j < this.expression.length(); j++) {
                    if (this.expression.charAt(j) == ')') {  
                        if (j != this.expression.length()) { // if last character
                            substring = this.expression.substring(i + 1, j) + this.expression.substring(j + 1);

                            if (this.expression.charAt(j+1) == '*') {
                                this.loopIndex = j - 1;
                                this.loopFlag = true;
                            }
                            break out;

                        } else {
                            substring = this.expression.substring(i + 1, j);
                            break out;
                        }            
                    }
                }
            }
        }
        return substring;
    }
}
