package sample;

//1. Defining a thread by extending Thread or implementing Runnable
class WorkerTask implements Runnable {
 @Override
 public void run() {
     System.out.println("Java 7 Worker Thread running: " + Thread.currentThread().getName());
 }
}

public class DefineStartDemoJ7 {
 public static void main(String[] args) {
     // 2. Instantiating a Thread
     WorkerTask task = new WorkerTask();
     Thread thread = new Thread(task, "Worker-1");

     // 3. Starting the Thread
     thread.start();
 }
}
