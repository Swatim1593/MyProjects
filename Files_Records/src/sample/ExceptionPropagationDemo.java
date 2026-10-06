package sample;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ExceptionPropagationDemo {

    // Low-Level Layer: Logs and Rethrows
    public static void readDatabaseConfig(String path) throws IOException {
        try {
            if (path == null || path.isEmpty()) {
                throw new FileNotFoundException("Config path cannot be empty.");
            }
            // Simulating an I/O Failure
            throw new IOException("Storage hardware failed to respond.");
        } catch (IOException e) {
            System.err.println("[LOG System] Recorded low-level I/O failure: " + e.getMessage());
            throw e; // Rethrowing exception up the call stack
        }
    }

    // Mid-Level Layer: Delegates up
    public static void initializeServices() throws IOException {
        readDatabaseConfig(""); // Propagates up
    }

    // Top-Level Execution Layer: Catches final propagated exception
    public static void main(String[] args) {
        try {
            initializeServices();
        } catch (IOException e) {
            System.out.println("[UI Layer] Displaying Error Dialog to User: " + e.getMessage());
        }
    }
}