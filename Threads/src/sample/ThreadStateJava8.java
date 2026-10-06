package sample;

import java.util.concurrent.CompletableFuture;

public class ThreadStateJava8 {
    public static void main(String[] args) throws Exception {
        CompletableFuture<String> futureTask = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Task Finished";
        });

        System.out.println("Is Done initially: " + futureTask.isDone()); // false

        Thread.sleep(200);
        System.out.println("Is Completed Exceptionally: " + futureTask.isCompletedExceptionally()); // false

        String result = futureTask.get(); // Blocking wait for result
        System.out.println("Result: " + result);
        System.out.println("Is Done after get(): " + futureTask.isDone()); // true
    }
}
