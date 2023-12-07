import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GrepyDriver {
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
	    System.out.print("Enter name of file: ");
        read_file(input.next());
    }

    public static void read_file(String fileName) throws FileNotFoundException {
        
	    Scanner inFile = new Scanner(new File(fileName));
    }
}
