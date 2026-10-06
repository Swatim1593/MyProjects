
package com.quickbite.entity;

import com.quickbite.strategy.Payment;
import java.time.LocalDate;
import java.util.List;

public class Order {
    private int orderId;
    private LocalDate orderDate;
    private double totalAmount;
    private OrderStatus status;
    private Customer customer;
    private List<OrderItem> items;
    private Payment payment;

    public Order(int orderId, Customer customer, List<OrderItem> items, Payment payment,double totalAmount) {
        this.orderId = orderId;
        this.customer = customer;
        this.items = items;
        this.totalAmount=totalAmount;
        this.payment = payment;
        this.orderDate = LocalDate.now();
        this.status = OrderStatus.CREATED;
        this.totalAmount = items.stream().mapToDouble(OrderItem::getSubtotal).sum();
    }

    public int getOrderId() { return orderId; }
    public LocalDate getOrderDate() { return orderDate; }
    public double getTotalAmount() { return totalAmount; }
    public OrderStatus getStatus() { return status; }
    public Customer getCustomer() { return customer; }
    public List<OrderItem> getItems() { return items; }
    public Payment getPayment() { return payment; }

    public void setStatus(OrderStatus status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("Order #%d | Date: %s | Customer: %s | Total: ₹%.2f | Status: %s", 
                orderId, orderDate, customer.getName(), totalAmount, status);
    }
}





