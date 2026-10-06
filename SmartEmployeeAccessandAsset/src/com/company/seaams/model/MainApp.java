package com.company.seaams.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.TreeMap;
import java.util.SortedMap;

import com.company.seaams.model.Employee;
import com.company.seaams.model.Laptop;
import com.company.seaams.model.Mobile;
import com.company.seaams.model.ServiceRequest;
import com.company.seaams.model.EmployeeService;
import com.company.seaams.model.RequestService;
import com.company.seaams.model.ValidationUtil;

public class MainApp {
    private static int requestSequenceCounter = 5001;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EmployeeService employeeService = new EmployeeService();
        AssetService assetService = new AssetService();
        RequestService requestService = new RequestService();

        // Seed Core Inventory Elements
        assetService.registerInventoryAsset(new Laptop("LAP1001", "MacBook Pro", 2499.00));
        assetService.registerInventoryAsset(new Laptop("LAP1002", "ThinkPad T14", 1350.50));
        assetService.registerInventoryAsset(new Mobile("MOB2001", "iPhone 15 Pro", 1099.00));
        assetService.registerInventoryAsset(new Mobile("MOB2002", "Galaxy S24", 899.99));

        int executionChoice = 0;
        System.out.println("=========================================================");
        System.out.println("  Smart Employee Asset & Access Management System (SEAAMS)");
        System.out.println("=========================================================");

        do {
            System.out.println("\n--- MAIN CONTROL PANEL INTERFACE ---");
            System.out.println("1. Register New Employee");
            System.out.println("2. Display All Registered Employees");
            System.out.println("3. Search Employee Record by ID");
            System.out.println("4. Allocate Asset to Employee");
            System.out.println("5. Deallocate / Return Asset");
            System.out.println("6. Display Global Asset Inventory status");
            System.out.println("7. Raise Service Request ticket (FIFO)");
            System.out.println("8. Process next pending Service Request Ticket");
            System.out.println("9. View all queued requests");
            System.out.println("10. Display Sorted Employees by Name");
            System.out.println("11. Advanced Navigation Demos (TreeSet / TreeMap / Backed)");
            System.out.println("12. Terminate Application System");
            System.out.print("Enter selection choice code: ");
            
            try {
                String inputLine = scanner.nextLine();
                executionChoice = Integer.parseInt(inputLine.trim());
            } catch (NumberFormatException e) {
                System.out.println("[ALERT] Invalid text format selection option entry choice received.");
                continue;
            }

            switch (executionChoice) {
                case 1:
                    try {
                        System.out.print("Enter Numeric Employee ID: ");
                        int id = ValidationUtil.parseEmployeeId(scanner.nextLine());
                        System.out.print("Enter Legal Full Name: ");
                        String name = scanner.nextLine().trim();
                        System.out.print("Enter Core Target Email Address: ");
                        String email = scanner.nextLine().trim();
                        System.out.print("Enter Financial Base Salary: ");
                        double salary = ValidationUtil.parseSalary(scanner.nextLine());
                        System.out.print("Enter Assignable Department Scope: ");
                        String dept = scanner.nextLine().trim();

                        Employee emp = new Employee(id, name, email, salary, dept);
                        if (employeeService.addEmployee(emp)) {
                            System.out.println("[SUCCESS] Personnel registration recorded accurately inside state memory layers.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("[CRITICAL CONVERSION REGISTRATION FAILURE] Input format parsing error.");
                    }
                    break;

                case 2:
                    System.out.println("\n--- Registered Employees ---");
                    employeeService.displayEmployees();
                    break;

                case 3:
                    System.out.print("Enter target Search ID token: ");
                    try {
                        int searchId = Integer.parseInt(scanner.nextLine().trim());
                        Employee foundEmp = employeeService.searchEmployee(searchId);
                        if (foundEmp != null) {
                            System.out.println("[FOUND] Match discovered:\n " + foundEmp);
                        } else {
                            System.out.println("[NOT FOUND] No corresponding structural identifier entry traced.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid numeric lookup syntax.");
                    }
                    break;

                case 4:
                    System.out.print("Enter precise Asset Tag (e.g., LAP1001): ");
                    String assignTag = scanner.nextLine().trim();
                    System.out.print("Enter target Employee ID field pointer: ");
                    try {
                        int empId = Integer.parseInt(scanner.nextLine().trim());
                        if (employeeService.searchEmployee(empId) == null) {
                            System.out.println("[DENIED] Employee record does not exist.");
                        } else {
                            assetService.allocateAsset(assignTag, empId);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid ID layout input syntax.");
                    }
                    break;

                case 5:
                    System.out.print("Enter Asset Tag variant to unassign: ");
                    String returnTag = scanner.nextLine().trim();
                    assetService.deallocateAsset(returnTag);
                    break;

                case 6:
                    System.out.println("\n--- Global Asset Inventory ---");
                    assetService.displayAllAssets();
                    break;

                case 7:
                    System.out.print("Enter Employee ID requesting assistance: ");
                    try {
                        int reqEmpId = Integer.parseInt(scanner.nextLine().trim());
                        if (employeeService.searchEmployee(reqEmpId) == null) {
                            System.out.println("[DENIED] Employee record must exist before adding a ticket.");
                            break;
                        }
                        System.out.print("Enter Request Details (e.g., PasswordReset): ");
                        String type = scanner.nextLine().trim();

                        ServiceRequest req = new ServiceRequest(requestSequenceCounter++, reqEmpId, type, "OPEN");
                        requestService.raiseRequest(req);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid payload numeric layout input syntax.");
                    }
                    break;

                case 8:
                    requestService.processNextRequest();
                    break;

                case 9:
                    System.out.println("\n--- Currently Queued Requests (FIFO Pipeline) ---");
                    requestService.listPendingRequests();
                    break;

                case 10:
                    System.out.println("\n--- Sorted Employee Directory Listing ---");
                    List<Employee> sortedList = employeeService.getEmployees();
                    if (sortedList.isEmpty()) {
                        System.out.println("No elements found to sort inside data store.");
                        break;
                    }
                    Collections.sort(sortedList, new Comparator<Employee>() {
                        @Override
                        public int compare(Employee e1, Employee e2) {
                            return e1.getName().compareToIgnoreCase(e2.getName());
                        }
                    });
                    for (Employee e : sortedList) {
                        System.out.println(e);
                    }
                    break;

                case 11:
                    executeAdvancedNavigationDemos(employeeService.getEmployees());
                    break;

                case 12:
                    System.out.println("Safely flushing engine files. System shutting down.");
                    break;

                default:
                    System.out.println("Menu operation index outside limits.");
            }
        } while (executionChoice != 12);

        scanner.close();
    }

    private static void executeAdvancedNavigationDemos(List<Employee> employees) {
        System.out.println("\n--- ADVANCED REGISTRY NAVIGATION ENGINE DEMO ---");
        
        // 1. TreeSet Demonstration
        TreeSet<Integer> employeeIds = new TreeSet<Integer>();
        employeeIds.add(Integer.valueOf(1005));
        employeeIds.add(Integer.valueOf(1010));
        employeeIds.add(Integer.valueOf(1020));
        employeeIds.add(Integer.valueOf(1030));
        
        System.out.println("Active TreeSet Matrix IDs: " + employeeIds);
        System.out.println("lower(1020)   [Strictly < 1020] : " + employeeIds.lower(Integer.valueOf(1020)));
        System.out.println("higher(1020)  [Strictly > 1020] : " + employeeIds.higher(Integer.valueOf(1020)));
        System.out.println("floor(1020)   [Values <= 1020] : " + employeeIds.floor(Integer.valueOf(1020)));
        System.out.println("ceiling(1021) [Values >= 1021] : " + employeeIds.ceiling(Integer.valueOf(1021)));

        // 2. TreeMap Demonstration
        TreeMap<Integer, String> hierarchyMap = new TreeMap<Integer, String>();
        hierarchyMap.put(Integer.valueOf(100), "CEO Office");
        hierarchyMap.put(Integer.valueOf(200), "VP Engineering");
        hierarchyMap.put(Integer.valueOf(300), "Director Operations");

        System.out.println("\nActive TreeMap Hierarchy Map: " + hierarchyMap);
        System.out.println("higherKey(200): " + hierarchyMap.higherKey(Integer.valueOf(200)));
        System.out.println("lowerKey(200):  " + hierarchyMap.lowerKey(Integer.valueOf(200)));
        System.out.println("firstKey():     " + hierarchyMap.firstKey());
        System.out.println("lastKey():      " + hierarchyMap.lastKey());

        // 3. Backed Collection View Window Demonstration
        TreeMap<Integer, String> masterReportMap = new TreeMap<Integer, String>();
        masterReportMap.put(Integer.valueOf(1000), "Emp_1000");
        masterReportMap.put(Integer.valueOf(1500), "Emp_1500");
        masterReportMap.put(Integer.valueOf(1800), "Emp_1800");
        masterReportMap.put(Integer.valueOf(2500), "Emp_2500");

        // Create a live view slice between 1000 (inclusive) and 2000 (exclusive)
        SortedMap<Integer, String> subMapWindow = masterReportMap.subMap(Integer.valueOf(1000), Integer.valueOf(2000));
        System.out.println("\nSubMap Window View (ID 1000-2000): " + subMapWindow);
        
        // Mutate the live view window
        subMapWindow.put(Integer.valueOf(1200), "Emp_1200_Added_To_SubView");
        System.out.println("Master Map State After Subview Injection: " + masterReportMap);
    }
}