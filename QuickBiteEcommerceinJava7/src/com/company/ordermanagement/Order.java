package com.company.ordermanagement;

import java.time.LocalDate;
import java.util.Optional;

public class Order {

    private final int orderId;
    private final Customer customer;
    private final double amount;
    private final String category;
    private final LocalDate orderDate; // Uses thread-safe, immutable LocalDate (JSR-310)

    public Order(int orderId, Customer customer, double amount, String category, LocalDate orderDate) {
        this.orderId = orderId;
        this.customer = customer;
        this.amount = amount;
        this.category = category;
        this.orderDate = orderDate;
    }

    public int getOrderId() {
        return orderId;
    }

    // Optional prevents NullPointerException if an order has a missing/guest customer
    public Optional<Customer> getCustomer() {
        return Optional.ofNullable(customer);
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    @Override
    public String toString() {
        String customerName = getCustomer()
                .map(Customer::getName)
                .orElse("Guest User");
        return "Order [id=" + orderId + ", customer=" + customerName + ", amount=" + amount + ", category=" + category + ", date=" + orderDate + "]";
    }
}