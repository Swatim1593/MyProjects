package sample;

public class ThreadCreationJava8 {
    public static void main(String[] args) {
        // Runnable is a @FunctionalInterface in Java 8
        Runnable runnableTask = () -> 
            System.out.println("Java 8 Lambda Runnable running: " + Thread.currentThread().getName());

        // Inline instantiation with Lambda
        Thread thread1 = new Thread(runnableTask);
        Thread thread2 = new Thread(() -> 
            System.out.println("Java 8 Inline Lambda running: " + Thread.currentThread().getName())
        );

        thread1.start();
        thread2.start();
    }
}