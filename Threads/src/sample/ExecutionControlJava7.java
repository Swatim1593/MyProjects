package sample;

public class ExecutionControlJava7 {
    public static void main(String[] args) {
        Thread worker = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 3; i++) {
                    System.out.println("Worker Step " + i);
                    try {
                        Thread.sleep(500); // Sleep demo
                    } catch (InterruptedException e) {
                        System.out.println("Worker interrupted");
                    }
                    Thread.yield(); // Yield CPU time slice to other threads
                }
            }
        });

        worker.start();

        try {
            System.out.println("Main thread waiting for Worker to finish...");
            worker.join(); // Join demo: Main blocks until worker completes
            System.out.println("Worker has completed. Main thread resumes.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}