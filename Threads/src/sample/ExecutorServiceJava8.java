package sample;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceJava8 {
    public static void main(String[] args) throws Exception {
        ExecutorService customPool = Executors.newFixedThreadPool(2);

        // Supply async result -> Transform result -> Consume result without blocking main thread
        CompletableFuture<Integer> asyncPipeline = CompletableFuture.supplyAsync(() -> {
            System.out.println("Calculating in background pool: " + Thread.currentThread().getName());
            return 21;
        }, customPool)
        .thenApply(val -> val * 2) // Transform
        .whenComplete((result, exception) -> {
            if (exception == null) {
                System.out.println("Async processing finished with result: " + result);
            }
        });

        asyncPipeline.get(); // Ensure execution finishes before exiting main
        customPool.shutdown();
    }
}