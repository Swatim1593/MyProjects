package sample;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CharacterStreamDemo {
    public static void main(String[] args) {
        String logPath = "audit_log.txt";

        // Writing text using FileWriter
        try (FileWriter writer = new FileWriter(logPath)) {
            writer.write("DRIVER_ID: KA-51-9982\nSTATUS: ACTIVE\nFARE: Rs.250.00\n");
            System.out.println("Text audit log written.");
        } catch (IOException e) {
            System.err.println("Write error: " + e.getMessage());
        }

        // Reading text using FileReader
        try (FileReader reader = new FileReader(logPath)) {
            int characterData;
            System.out.println("--- Reading Audit Log ---");
            while ((characterData = reader.read()) != -1) {
                System.out.print((char) characterData);
            }
        } catch (IOException e) {
            System.err.println("Read error: " + e.getMessage());
        }
    }
}