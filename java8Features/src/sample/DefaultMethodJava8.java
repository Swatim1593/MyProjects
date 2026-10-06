package sample;

//Java 8 Interface with default and static methods
interface Vehicle {
 void start();

 // Default method added without breaking existing implementations
 default void stop() {
     System.out.println("[JAVA 8 DEFAULT] Vehicle stopped safely (Default Implementation)");
 }

 // Static utility method directly inside interface
 static void serviceInfo() {
     System.out.println("[JAVA 8 STATIC] Official Transport Service Utility Check");
 }
}

//Second Interface declaring conflicting default method
interface SecurityAlarm {
 default void stop() {
     System.out.println("[JAVA 8 ALARM] Security Alarm deactivated");
 }
}

//Class 1: AutoRickshaw inherits default stop() automatically
class AutoRickshaw implements Vehicle {
 @Override
 public void start() {
     System.out.println("[JAVA 8 AUTO] AutoRickshaw started by Ramesh");
 }
 // 'stop()' is inherited automatically from Vehicle — zero code breakage!
}

//Class 2: KSRTCBus overrides default stop() with custom behavior
class KSRTCBus implements Vehicle {
 @Override
 public void start() {
     System.out.println("[JAVA 8 BUS] KSRTC Bus started by Suresh");
 }

 @Override
 public void stop() {
     System.out.println("[JAVA 8 BUS] Air brakes applied. KSRTC Bus stopped by Suresh.");
 }
}

//Class 3: Resolving Conflict between Vehicle.stop() and SecurityAlarm.stop()
class PrivateSUV implements Vehicle, SecurityAlarm {
 @Override
 public void start() {
     System.out.println("[JAVA 8 SUV] Private SUV engine started by Karthik");
 }

 // Rule 3: Explicit Ambiguity Resolution required using Interface.super.methodName()
 @Override
 public void stop() {
     System.out.println("\n--- Resolving Interface Conflict for SUV ---");
     Vehicle.super.stop();       // Explicitly invoking Vehicle's default implementation
     SecurityAlarm.super.stop(); // Explicitly invoking SecurityAlarm's default implementation
 }
}

public class DefaultMethodJava8 {
 public static void main(String[] args) {

     System.out.println("=== 1. DEFAULT METHODS & BACKWARDS COMPATIBILITY ===");
     AutoRickshaw auto = new AutoRickshaw();
     auto.start();
     auto.stop(); // Executes Vehicle default body

     System.out.println();

     KSRTCBus bus = new KSRTCBus();
     bus.start();
     bus.stop(); // Executes overridden KSRTCBus body


     System.out.println("\n=== 2. STATIC METHODS IN INTERFACES ===");
     Vehicle.serviceInfo(); // Static call directly on Interface


     System.out.println("\n=== 3. MULTIPLE INHERITANCE CONFLICT RESOLUTION ===");
     PrivateSUV suv = new PrivateSUV();
     suv.start();
     suv.stop(); // Executes explicitly resolved interface calls
 }
}