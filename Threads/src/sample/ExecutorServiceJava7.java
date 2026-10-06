package sample;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceJava7 {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Callable<Integer> calculationTask = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("Calculating result inside: " + Thread.currentThread().getName());
                Thread.sleep(500);
                return 42;
            }
        };

        Future<Integer> futureResult = executor.submit(calculationTask);

        // Blocking call to get result from thread pool task
        Integer result = futureResult.get();
        System.out.println("Result received: " + result);

        executor.shutdown();
    }
}