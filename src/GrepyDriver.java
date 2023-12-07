import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class GrepyDriver {

    // C:\Users\giann\Desktop\Java\grepy\bin>
    // java --enable-preview GrepyDriver

    static String[] call_data = new String[4];
    // 0 - NFA fileName
    // 1 - DFA fileName
    // 2 - regular expression
    // 3 - input fileName

    public static boolean verifyRegex(String exp) {
        for(int i = 0; i < exp.length(); i++) {
            char ch = exp.charAt(i);
            
            // check digits, uppercase, and lowercase
            if(Character.isDigit(ch) || Character.isAlphabetic(ch)) {
                continue;
            // check special characters
            } else if (ch == '(' || ch == ')' || ch == '*' || ch == '+') {
                continue;
            // any character remaining is invalid
            } else {
                return false;
            }
        }
        // if loop iterates with no invalid character detection, return true
        return true;
    }

    public static boolean verifyFile(String fileName) {
        File testFile = new File(fileName);
        return testFile.exists();
    }

    public static void main(String[] args) throws Exception {
        // args = [-n, NFAfile, -d, DFAfile, regex, inputFile, additional]
        //System.out.println(Arrays.toString(args));

        try {

            // check for minimum amount of arguments for computation
            if(args.length < 2) {
                throw new IllegalArgumentException("Not enough vaild arguments. Enter a regular expression and an input file.");
            }

            // flags for input checking
            String input = Arrays.toString(args);
            boolean nfaFlag = false;
            boolean dfaFlag = false;

            // check each input flag and load call_data

            // check for NFA (optional)
            if(input.contains("-n")) {
                call_data[0] = args[1];
                nfaFlag = true;
            }

            // check for DFA (optional)
            if(input.contains("-d")) {
                call_data[1] = args[3];
                dfaFlag = true;
            }

            // check for regular expression and input fileName
            if(nfaFlag && dfaFlag) {                            // both NFA and DFA files provided
                call_data[2] = args[4]; // regex
                call_data[3] = args[5]; // filename
            } else if(nfaFlag ^ dfaFlag) {                      // either NFA or DFA file provided (not both)
                call_data[2] = args[2]; // regex
                call_data[3] = args[3]; // filename
            } else {                                            // no NFA or DFA files provided
                call_data[2] = args[0]; // regex
                call_data[3] = args[1]; // filename
            }
            
            // verify accepted characters, otherwise throw exception
            if(!verifyRegex(call_data[2])) {
                throw new IllegalArgumentException("Regular expression contains invalid characters.");
            }

            // verify file exists, otherwilse throw exception
            if(!verifyFile(call_data[3])) {
                throw new FileNotFoundException("Cannot locate input file.");
            }

            // create NFA

            // create DFA

            System.out.println(Arrays.toString(call_data));

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
