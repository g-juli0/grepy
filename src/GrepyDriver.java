import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GrepyDriver {

    // C:\Users\giann\Desktop\Java\grepy\bin>
    // java --enable-preview GrepyDriver

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
	    System.out.print("Enter name of file: ");
        input.next();
        //read_file(input.next());
    }

    public static void read_file(String fileName) throws FileNotFoundException {
        
	    Scanner inFile = new Scanner(new File(fileName));
    }
}
