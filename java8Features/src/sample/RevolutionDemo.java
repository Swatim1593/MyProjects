package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Employee {
    private String name;
    private double salary;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public double getSalary() { return salary; }
    public String getName() { return name; }
}

public class RevolutionDemo {
    public static void main(String[] args) {
        // Sample data using South Indian names
        List<Employee> employees = List.of(
            new Employee("Karthik", 60000),
            new Employee("Ananya", 45000),
            new Employee("Suresh", 75000)
        );

        // --- Java 7 Approach (Imperative style) ---
        List<Employee> highEarnersJava7 = new ArrayList<>();
        for (Employee e : employees) {
            if (e.getSalary() > 50000) {
                highEarnersJava7.add(e);
            }
        }

        // --- Java 8 Approach (Declarative Stream style) ---
        List<Employee> highEarnersJava8 = employees.stream()
            .filter(e -> e.getSalary() > 50000)
            .collect(Collectors.toList());

        // Output results
        System.out.println("Java 7 Count: " + highEarnersJava7.size());
        System.out.println("Java 8 Count: " + highEarnersJava8.size());
    }
}