
package sample;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

class ResourceJava8 {
    private final String id;
    public ResourceJava8(String id) { this.id = id; }
    public String getId() { return id; }
}

public class DeadlockPreventionJava8 {
    public static void main(String[] args) {
        ResourceJava8 r1 = new ResourceJava8("Resource-X");
        ResourceJava8 r2 = new ResourceJava8("Resource-Y");
        List<ResourceJava8> resources = Arrays.asList(r2, r1); // Out of order list

        Runnable safeTask = () -> {
            // Sort resources deterministically using Java 8 Stream API before acquiring locks
            ResourceJava8[] sortedResources = resources.stream()
                .sorted(Comparator.comparing(ResourceJava8::getId))
                .toArray(ResourceJava8[]::new);

            synchronized (sortedResources[0]) {
                System.out.println(Thread.currentThread().getName() + " locked " + sortedResources[0].getId());
                synchronized (sortedResources[1]) {
                    System.out.println(Thread.currentThread().getName() + " locked " + sortedResources[1].getId());
                }
            }
        };

        new Thread(safeTask, "LambdaWorker-1").start();
        new Thread(safeTask, "LambdaWorker-2").start();
    }
}