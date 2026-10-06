package sample;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Employees {
    private String name;
    private double salary;

    public Employees(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getName() { return name; }
    public double getSalary() { return salary; }

    @Override
    public String toString() {
        return name + " (₹" + (long)salary + ")";
    }
}

public class LambdaDeepDiveDemo {
    public static void main(String[] args) {
        System.out.println("=== 1. THREAD CREATION & BEHAVIOR PASSING ===");
        
        // --- Java 7: Anonymous Inner Class ---
        // Generates a separate class file on disk (LambdaDeepDiveDemo$1.class)
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("[Java 7 Anonymous Class] Calculating tax deductions for Karthik...");
            }
        };

        // --- Java 8: Lambda Expression ---
        // Uses 'invokedynamic' instruction; zero extra .class files created!
        Runnable r2 = () -> System.out.println("[Java 8 Lambda] Calculating tax deductions for Ananya...");

        new Thread(r1).start();
        new Thread(r2).start();

        // Pause main thread briefly to allow background threads to finish logging
        try { Thread.sleep(100); } catch (InterruptedException ignored) {}

        System.out.println("\n=== 2. COMPARATOR SORTING DEMO ===");

        List<Employee> team = new ArrayList<>();
        team.add(new Employee("Suresh", 75000));
        team.add(new Employee("Meenakshi", 52000));
        team.add(new Employee("Lakshmi", 68000));
        team.add(new Employee("Venkat", 45000));

        System.out.println("Original Employee Roster: " + team);

        // --- Java 7: Sorting via Anonymous Inner Class ---
        Collections.sort(team, new Comparator<Employee>() {
            @Override
            public int compare(Employee e1, Employee e2) {
                return Double.compare(e1.getSalary(), e2.getSalary());
            }
        });
        System.out.println("\n[Java 7 Anonymous Class] Sorted by Salary (Ascending): " + team);

        // --- Java 8: Sorting via Lambda Expression ---
        // Concise syntax with type inference and no explicit return for single expressions
        team.sort((e1, e2) -> Double.compare(e2.getSalary(), e1.getSalary()));
        System.out.println("[Java 8 Lambda] Sorted by Salary (Descending): " + team);
    }

}
