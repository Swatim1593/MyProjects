package sample;

import java.util.ArrayList;
import java.util.List;

// Java 7 Core Interface
interface Vehicle1 {
    void start();
    // IF WE ADDED 'void stop();' HERE IN JAVA 7, ALL IMPLEMENTING CLASSES WOULD BREAK!
}

// Java 7 Workaround: Abstract Adapter Class to support future method additions
abstract class AbstractVehicleAdapter implements Vehicle {
    // Adapter provides default behavior to prevent class compilation breakage
    public void stop() {
        System.out.println("[JAVA 7 DEFAULT] Vehicle engine stopped safely.");
    }
}

// Class 1: AutoRickshaw (Extends Adapter)
class AutoRickshaw1 extends AbstractVehicleAdapter {
    @Override
    public void start() {
        System.out.println("[JAVA 7 AUTO] AutoRickshaw started by Ramesh");
    }
}

// Class 2: KSRTC Bus (Extends Adapter)
class KSRTCBus1 extends AbstractVehicleAdapter {
    @Override
    public void start() {
        System.out.println("[JAVA 7 BUS] KSRTC Bus started by Suresh");
    }

    // Custom stop logic
    @Override
    public void stop() {
        System.out.println("[JAVA 7 BUS] Air brakes applied. KSRTC Bus stopped by Suresh.");
    }
}

public class DefaultMethodJava7 {
    public static void main(String[] args) {
        AutoRickshaw auto = new AutoRickshaw();
        KSRTCBus bus = new KSRTCBus();

        auto.start();
        auto.stop(); // Inherited from AbstractVehicleAdapter

        System.out.println();

        bus.start();
        bus.stop(); // Overridden in KSRTCBus
    }
}
