package com.company.ordermanagement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class OrderManagementApp {

    public static void main(String[] args) {
        // Formatter using Java 8 JSR-310 API
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 1. Instantiating Domain Data with LocalDate
        Customer john = new Customer(1, "John", "Hyderabad", true);
        Customer david = new Customer(2, "David", "Bangalore", false);
        Customer smith = new Customer(3, "Smith", "Chennai", true);

        List<Order> orders = Arrays.asList(
            new Order(101, john, 45000.0, "Electronics", LocalDate.parse("2025-01-10", formatter)),
            new Order(102, david, 1200.0, "Books", LocalDate.parse("2025-02-15", formatter)),
            new Order(103, smith, 3500.0, "Fashion", LocalDate.parse("2025-03-20", formatter)),
            new Order(104, john, 52000.0, "Electronics", LocalDate.parse("2025-04-05", formatter)),
            new Order(105, null, 8900.0, "Electronics", LocalDate.parse("2025-05-12", formatter)) // Guest Order (Null safety test)
        );

        ReportService reportService = new ReportService();

        // --- 1. ELECTRONICS ORDERS ---
        System.out.println("=== 1. ELECTRONICS ORDERS (JAVA 8 STREAMS) ===");
        reportService.getElectronicsOrders(orders)
                     .forEach(System.out::println); // Method reference

        // --- 2. HIGH VALUE ORDERS (> 10000) ---
        System.out.println("\n=== 2. HIGH VALUE ORDERS (> 10000) ===");
        reportService.getHighValueOrders(orders, 10000.0)
                     .forEach(System.out::println);

        // --- 3. TOTAL REVENUE ---
        System.out.println("\n=== 3. TOTAL REVENUE ===");
        System.out.println("Total Revenue: $" + reportService.getTotalRevenue(orders));

        // --- 4. CATEGORY REPORT (GROUPING BY) ---
        System.out.println("\n=== 4. CATEGORY COUNT REPORT (COLLECTORS.GROUPINGBY) ===");
        Map<String, Long> categoryCounts = reportService.categoryReport(orders);
        categoryCounts.forEach((category, count) -> System.out.println(category + " -> " + count + " order(s)"));

        // --- 5. PARTITIONING BY PREMIUM STATUS ---
        System.out.println("\n=== 5. PARTITIONING ORDERS (PREMIUM VS REGULAR) ===");
        Map<Boolean, List<Order>> partitioned = reportService.partitionByPremiumStatus(orders);
        System.out.println("Premium Orders Count: " + partitioned.get(true).size());
        System.out.println("Regular/Guest Orders Count: " + partitioned.get(false).size());

        // --- 6. DISCOUNT CALCULATIONS (LAMBDAS INSTEAD OF EXTRA CLASSES) ---
        System.out.println("\n=== 6. DISCOUNT CALCULATIONS (DYNAMIC LAMBDAS) ===");
        DiscountStrategy premiumDiscount = amount -> amount * 0.15; // Replaces PremiumDiscount.java
        DiscountStrategy regularDiscount = amount -> amount * 0.05; // Replaces RegularDiscount.java

        orders.forEach(order -> {
            boolean isPremium = order.getCustomer().map(Customer::isPremium).orElse(false);
            DiscountStrategy strategy = isPremium ? premiumDiscount : regularDiscount;
            double discount = strategy.calculateDiscount(order.getAmount());
            
            String customerName = order.getCustomer().map(Customer::getName).orElse("Guest");
            System.out.print("Order #" + order.getOrderId() + " (" + customerName + ") -> ");
            strategy.printDiscountNotice(order.getAmount(), discount);
        });
    }
}