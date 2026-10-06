package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Dev {
    private String name;
    private String department;
    private double salary;
    private int age;

    public Dev(String name, String department, double salary, int age) {
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.age = age;
    }

    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }
    public int getAge() { return age; }

    @Override
    public String toString() {
        return name + " (" + department + ", ₹" + (long)salary + ")";
    }
}

public class StreamJava7Demo {
    public static void main(String[] args) {
        List<Dev> devs = new ArrayList<Dev>();
        devs.add(new Dev("Karthik", "IT", 85000, 30));
        devs.add(new Dev("Ananya", "HR", 55000, 45));
        devs.add(new Dev("Suresh", "IT", 95000, 28));
        devs.add(new Dev("Meenakshi", "HR", 60000, 35));
        devs.add(new Dev("Lakshmi", "IT", 72000, 26));

        System.out.println("=== 1. FILTER, TRANSFORM, AND LIMIT (Java 7) ===");
        List<String> highEarnerNames = new ArrayList<String>();
        int matchCount = 0;

        for (Dev d : devs) {
            System.out.println("Evaluating: " + d.getName());
            if (d.getSalary() >= 60000) {
                System.out.println("Converting: " + d.getName());
                highEarnerNames.add(d.getName().toUpperCase());
                matchCount++;
                if (matchCount == 2) { // Manual limit / short-circuit implementation
                    break;
                }
            }
        }
        System.out.println("Filtered Names (Max 2): " + highEarnerNames);


        System.out.println("\n=== 2. GROUP BY DEPARTMENT (Java 7) ===");
        Map<String, List<Dev>> byDept = new HashMap<String, List<Dev>>();
        for (Dev d : devs) {
            String dept = d.getDepartment();
            if (!byDept.containsKey(dept)) {
                byDept.put(dept, new ArrayList<Dev>());
            }
            byDept.get(dept).add(d);
        }
        System.out.println("IT Department Count: " + byDept.get("IT").size());


        System.out.println("\n=== 3. PARTITION BY SALARY > 60000 (Java 7) ===");
        Map<Boolean, List<Dev>> partitioned = new HashMap<Boolean, List<Dev>>();
        partitioned.put(true, new ArrayList<Dev>());
        partitioned.put(false, new ArrayList<Dev>());

        for (Dev d : devs) {
            boolean isHigh = d.getSalary() > 60000;
            partitioned.get(isHigh).add(d);
        }
        System.out.println("High Earners Count (>60k): " + partitioned.get(true).size());


        System.out.println("\n=== 4. MANUAL AGE STATISTICS (Java 7) ===");
        int sumAge = 0;
        int minAge = Integer.MAX_VALUE;
        int maxAge = Integer.MIN_VALUE;

        for (Dev d : devs) {
            int age = d.getAge();
            sumAge += age;
            if (age < minAge) minAge = age;
            if (age > maxAge) maxAge = age;
        }
        double avgAge = (double) sumAge / devs.size();

        System.out.println("Average Age : " + avgAge);
        System.out.println("Max Age     : " + maxAge);
        System.out.println("Min Age     : " + minAge);
    }
}