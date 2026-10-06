package sample;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProducerConsumerJava8 {
    public static void main(String[] args) {
        // High-level blocking queue that manages wait/notify internally
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(1);
        ExecutorService pool = Executors.newFixedThreadPool(2);

        // Producer Task
        pool.submit(() -> {
            try {
                String[] items = {"Msg 1", "Msg 2", "DONE"};
                for (String item : items) {
                    queue.put(item); // Automatically blocks when full (replaces wait/notify)
                    System.out.println("Produced: " + item);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Consumer Task
        pool.submit(() -> {
            try {
                String msg;
                while (!(msg = queue.take()).equals("DONE")) { // Automatically blocks when empty
                    System.out.println("Consumed: " + msg);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        pool.shutdown();
    }
}