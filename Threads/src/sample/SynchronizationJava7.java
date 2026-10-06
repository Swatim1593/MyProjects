package sample;

class BankAccountJava7 {
    private int balance = 1000;
    private final Object lock = new Object();

    // Synchronized Block with explicit lock object
    public void withdraw(int amount) {
        synchronized (lock) {
            if (balance >= amount) {
                System.out.println(Thread.currentThread().getName() + " withdrawing " + amount);
                balance -= amount;
                System.out.println(Thread.currentThread().getName() + " remaining balance: " + balance);
            } else {
                System.out.println(Thread.currentThread().getName() + " - Insufficient funds");
            }
        }
    }
}

public class SynchronizationJava7 {
    public static void main(String[] args) {
        final BankAccountJava7 account = new BankAccountJava7();

        Runnable task = new Runnable() {
            @Override
            public void run() {
                account.withdraw(700);
            }
        };

        new Thread(task, "Thread-A").start();
        new Thread(task, "Thread-B").start();
    }
}