package sample;

public class ThreadCreationJava7 {
    public static void main(String[] args) {
        // Approach 1: Extending Thread Class
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                System.out.println("Java 7 Thread subclass running: " + Thread.currentThread().getName());
            }
        };

        // Approach 2: Implementing Runnable via Anonymous Inner Class
        Runnable runnableTask = new Runnable() {
            @Override
            public void run() {
                System.out.println("Java 7 Anonymous Runnable running: " + Thread.currentThread().getName());
            }
        };
        Thread thread2 = new Thread(runnableTask);

        thread1.start();
        thread2.start();
    }
}