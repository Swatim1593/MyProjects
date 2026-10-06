package sample;

class ResourceJava7 {
    private final String id;
    public ResourceJava7(String id) { this.id = id; }
    public String getId() { return id; }
}

public class DeadlockPreventionJava7 {
    private final ResourceJava7 resA = new ResourceJava7("RES_A");
    private final ResourceJava7 resB = new ResourceJava7("RES_B");

    public void executeTask() {
        // Always lock resources in alphabetical order based on ID to avoid deadlocks
        ResourceJava7 firstLock = resA.getId().compareTo(resB.getId()) < 0 ? resA : resB;
        ResourceJava7 secondLock = firstLock == resA ? resB : resA;

        synchronized (firstLock) {
            System.out.println(Thread.currentThread().getName() + " acquired " + firstLock.getId());
            synchronized (secondLock) {
                System.out.println(Thread.currentThread().getName() + " acquired " + secondLock.getId());
            }
        }
    }

    public static void main(String[] args) {
        final DeadlockPreventionJava7 demo = new DeadlockPreventionJava7();
        
        new Thread(new Runnable() {
            @Override
            public void run() { demo.executeTask(); }
        }, "Worker-1").start();

        new Thread(new Runnable() {
            @Override
            public void run() { demo.executeTask(); }
        }, "Worker-2").start();
    }
}