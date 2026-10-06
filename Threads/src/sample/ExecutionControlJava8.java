package sample;

import java.util.concurrent.CompletableFuture;

public class ExecutionControlJava8 {
    public static void main(String[] args) throws Exception {
        // Asynchronous execution replacing manual join() calls
        CompletableFuture<Void> pipeline = CompletableFuture.runAsync(() -> {
            for (int i = 1; i <= 3; i++) {
                System.out.println("Worker Step " + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).thenRun(() -> System.out.println("Continuation task executed automatically without explicit join()!"));

        // Wait for pipeline completion
        pipeline.get();
    }
}