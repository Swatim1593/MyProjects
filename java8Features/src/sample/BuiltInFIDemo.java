package sample;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

class Customer {
    private String name;
    private int age;
    private double monthlySalary;
    private boolean activeStatus;

    public Customer(String name, int age, double monthlySalary, boolean activeStatus) {
        this.name = name;
        this.age = age;
        this.monthlySalary = monthlySalary;
        this.activeStatus = activeStatus;
    }

    public String getName() {
    	return name; 
    	}
    public int getAge() { 
    	return age; }
    public double getMonthlySalary() { return monthlySalary; }
    public boolean isActiveStatus() { return activeStatus; }

    @Override
    public String toString() {
        return name + " [Age=" + age + ", Salary=₹" + monthlySalary + ", Active=" + activeStatus + "]";
    }
}

public class BuiltInFIDemo {
    public static void main(String[] args) {

        List<Customer> customers = List.of(
            new Customer("Aarav", 28, 65000, true),
            new Customer("Meenakshi", 21, 42000, true),
            new Customer("Lakshmi", 35, 80000, false),
            new Customer("Venkat", 19, 25000, true)
        );

        System.out.println("=== 1. PREDICATE & FUNCTIONAL COMPOSITION (Loan Approval) ===");
        
        // Individual Predicates
        Predicate<Customer> isAgeEligible = c -> c.getAge() >= 21;
        Predicate<Customer> isSalaryEligible = c -> c.getMonthlySalary() >= 50000;
        Predicate<Customer> isActive = Customer::isActiveStatus;

        // Composing Predicates using .and()
        Predicate<Customer> loanApprovalCheck = isAgeEligible.and(isSalaryEligible).and(isActive);

        for (Customer c : customers) {
            boolean approved = loanApprovalCheck.test(c);
            System.out.println("Loan Approval for " + c.getName() + " : " + (approved ? "APPROVED" : "REJECTED"));
        }

        System.out.println("\n=== 2. FUNCTION (Data Transformation & Chaining) ===");
        
        // Function 1: Extract monthly salary from Customer
        Function<Customer, Double> extractSalary = Customer::getMonthlySalary;

        // Function 2: Calculate annual salary from monthly salary
        Function<Double, Double> calculateAnnual = monthly -> monthly * 12;

        // Chaining Functions using .andThen()
        Function<Customer, Double> getAnnualSalary = extractSalary.andThen(calculateAnnual);

        Customer aarav = customers.get(0);
        System.out.println("Annual Package for " + aarav.getName() + " : ₹" + getAnnualSalary.apply(aarav));

        System.out.println("\n=== 3. CONSUMER (Side Effects & Chaining) ===");
        
        // Consumer 1: Print audit log
        Consumer<Customer> auditLogger = c -> System.out.println("[AUDIT LOG] Customer Record: " + c.getName());

        // Consumer 2: Send notification message
        Consumer<Customer> emailNotifier = c -> System.out.println("[EMAIL SENT] Welcome email dispatched to " + c.getName());

        // Chaining Consumers using .andThen()
        Consumer<Customer> onboardProcess = auditLogger.andThen(emailNotifier);

        // Execute chain on active customers
        customers.stream()
                 .filter(isActive)
                 .forEach(onboardProcess);

        System.out.println("\n=== 4. SUPPLIER (Lazy Generation & Default Values) ===");
        
        // Supplier generating system transaction timestamps
        Supplier<String> timestampSupplier = () -> LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss"));

        System.out.println("System Audit Timestamp : " + timestampSupplier.get());

        // Supplier providing default customer fallback
        Supplier<Customer> defaultCustomerSupplier = () -> new Customer("GUEST_USER", 0, 0.0, false);
        
        List<Customer> emptyList = new ArrayList<>();
        Customer resolvedCustomer = emptyList.stream()
                                              .findFirst()
                                              .orElseGet(defaultCustomerSupplier);

        System.out.println("Resolved Fallback User: " + resolvedCustomer);
    }
}