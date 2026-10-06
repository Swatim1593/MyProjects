package com.company.ordermanagement;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportService {

    // 1. Filter by Category using Streams
    public List<Order> getElectronicsOrders(List<Order> orders) {
        return orders.stream()
                .filter(order -> "Electronics".equalsIgnoreCase(order.getCategory()))
                .collect(Collectors.toList());
    }

    // 2. Filter High Value Orders (> minAmount)
    public List<Order> getHighValueOrders(List<Order> orders, double minAmount) {
        return orders.stream()
                .filter(order -> order.getAmount() > minAmount)
                .collect(Collectors.toList());
    }

    // 3. Total Revenue using mapToDouble and sum
    public double getTotalRevenue(List<Order> orders) {
        return orders.stream()
                .mapToDouble(Order::getAmount)
                .sum();
    }

    // 4. Category Grouping & Counting using Collectors.groupingBy
    public Map<String, Long> categoryReport(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.groupingBy(
                        Order::getCategory,
                        Collectors.counting()
                ));
    }

    // 5. Partitioning by Premium Customers vs Regular Customers
    public Map<Boolean, List<Order>> partitionByPremiumStatus(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.partitioningBy(
                        order -> order.getCustomer()
                                      .map(Customer::isPremium)
                                      .orElse(false)
                ));
    }

    // 6. Filter Premium Customer Orders
    public List<Order> getPremiumOrders(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getCustomer()
                                      .map(Customer::isPremium)
                                      .orElse(false))
                .collect(Collectors.toList());
    }
}