package com.company.seaams.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.company.seaams.model.Employee;

public class EmployeeService {
    private final List<Employee> employees = new ArrayList<Employee>();
    private final Set<String> uniqueEmails = new HashSet<String>();
    private final Map<Integer, Employee> employeeMap = new HashMap<Integer, Employee>();

    public boolean addEmployee(Employee emp) {
        // Enforce internal business safeguards using Set evaluation
        if (uniqueEmails.contains(emp.getEmail().toLowerCase())) {
            System.out.println("[CRITICAL ERROR] Registration Rejected: Email target already exists!");
            return false;
        }
        if (employeeMap.containsKey(Integer.valueOf(emp.getId()))) {
            System.out.println("[CRITICAL ERROR] Registration Rejected: Employee ID allocation collision!");
            return false;
        }

        employees.add(emp);
        uniqueEmails.add(emp.getEmail().toLowerCase());
        employeeMap.put(Integer.valueOf(emp.getId()), emp);
        return true;
    }

    public void displayEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No records found inside memory.");
            return;
        }
        for (Employee e : employees) {
            System.out.println(e);
        }
    }

    public Employee searchEmployee(int id) {
        return employeeMap.get(Integer.valueOf(id));
    }

    public List<Employee> getEmployees() {
        return this.employees;
    }
}