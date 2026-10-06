package sample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

class BankAccountJava8 {
    // Lock-free thread-safe atomic variable
    private final AtomicInteger balance = new AtomicInteger(1000);

    public void withdraw(int amount) {
        // Atomic compare-and-swap mechanism
        balance.updateAndGet(current -> {
            if (current >= amount) {
                System.out.println(Thread.currentThread().getName() + " withdrawing " + amount);
                return current - amount;
            } else {
                System.out.println(Thread.currentThread().getName() + " - Insufficient funds");
                return current;
            }
        });
    }
}

public class SynchronizationJava8 {
    public static void main(String[] args) {
        BankAccountJava8 account = new BankAccountJava8();
        ExecutorService pool = Executors.newFixedThreadPool(2);

        // Submit concurrent withdrawal requests using method references / lambdas
        pool.submit(() -> account.withdraw(700));
        pool.submit(() -> account.withdraw(700));

        pool.shutdown();
    }
}