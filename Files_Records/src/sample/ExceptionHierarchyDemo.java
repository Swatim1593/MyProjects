package sample;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ExceptionHierarchyDemo {
    public static void main(String[] args) {
        try {
            FileReader fr = new FileReader("missing.txt");
            fr.read();
        } 
        // 1. SPECIFIC SUBCLASS FIRST
        catch (FileNotFoundException e) {
            System.err.println("Handled Specific Error: File not found on disk.");
        } 
        // 2. GENERIC PARENT CLASS SECOND
        catch (IOException e) {
            System.err.println("Handled Generic Error: General I/O issue.");
        } 
        // 3. TOP-LEVEL CATCH-ALL LAST
        catch (Exception e) {
            System.err.println("Handled Catch-All: " + e.getMessage());
        }
    }
}