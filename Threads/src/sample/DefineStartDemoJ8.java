package sample;

public class DefineStartDemoJ8 {
    public static void main(String[] args) {
        // Defining & Instantiating using a Lambda Expression (Runnable is a @FunctionalInterface)
        Thread thread = new Thread(() -> 
            System.out.println("Java 8 Worker Thread running: " + Thread.currentThread().getName()), 
            "Worker-Lambda"
        );

        // Starting the Thread
        thread.start();
    }
}