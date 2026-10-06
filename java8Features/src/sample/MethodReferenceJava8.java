package sample;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

class Customer2 {
    private String name;

    public Customer2() {
        this.name = "Guest User";
    }

    // Constructor targeted by Customer::new
    public Customer2(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // Static method targeted by Customer::calculateTax
    public static double calculateTax(double salary) {
        return salary * 0.10; // 10% TDS Tax
    }
}

class ConsolePrinter1 {
    // Instance method targeted by printer::printInfo
    public void printInfo(String message) {
        System.out.println("[CONSOLE LOG] " + message);
    }
}

public class MethodReferenceJava8 {
    public static void main(String[] args) {

        System.out.println("=== 1. STATIC METHOD REFERENCE (Class::staticMethod) ===");
        // Lambda: salary -> Customer.calculateTax(salary)
        Function<Double, Double> taxCalculator = Customer2::calculateTax;
        System.out.println("Tax for Karthik (₹60,000 base): ₹" + taxCalculator.apply(60000.0));


        System.out.println("\n=== 2. INSTANCE METHOD OF A SPECIFIC OBJECT (object::instanceMethod) ===");
        ConsolePrinter printer = new ConsolePrinter();
        // Lambda: msg -> printer.printInfo(msg)
        Consumer<String> logConsumer = printer::printInfo;
        logConsumer.accept("Payroll processed successfully for Ananya");


        System.out.println("\n=== 3. ARBITRARY INSTANCE METHOD OF A TYPE (Class::instanceMethod) ===");
        List<String> customerNames = Arrays.asList("Suresh", "Ananya", "Karthik", "Meenakshi");

        // String::toUpperCase  equivalent to  (String s) -> s.toUpperCase()
        // System.out::println  equivalent to  (String s) -> System.out.println(s)
        System.out.println("Converted Customer Names:");
        customerNames.stream()
                     .map(String::toUpperCase)
                     .forEach(System.out::println);


        System.out.println("\n=== 4. CONSTRUCTOR REFERENCE (Class::new) ===");
        // Customer::new  equivalent to  (String name) -> new Customer(name)
        Function<String, Customer2> customerFactory = Customer2::new;

        Customer2 c1 = customerFactory.apply("Lakshmi");
        Customer2 c2 = customerFactory.apply("Venkat");

        System.out.println("Dynamically Onboarded Customer 1: " + c1.getName());
        System.out.println("Dynamically Onboarded Customer 2: " + c2.getName());
    }
}