package com.quickbite.service;

import com.quickbite.dao.impl.OrderDAOImpl;
import com.quickbite.entity.*;
import com.quickbite.observer.OrderSubject;
import com.quickbite.strategy.Payment;
import java.util.List;

public class OrderService {
    private OrderDAOImpl orderDAO;
    private InventoryService inventoryService;
    private OrderSubject orderSubject;

    public OrderService(OrderDAOImpl orderDAO, InventoryService inventoryService, OrderSubject orderSubject) {
        this.orderDAO = orderDAO;
        this.inventoryService = inventoryService;
        this.orderSubject = orderSubject;
    }

    public Order createOrder(int orderId, Customer customer, List<OrderItem> items, Payment payment,double finalAmount) {
        // Validate and deduct stock
        for (OrderItem item : items) {
            inventoryService.deductStock(item.getProduct().getProductId(), item.getQuantity());
        }

        // Process payment
        payment.processPayment();

        Order order = new Order(orderId, customer, items, payment,finalAmount);
        order.setStatus(OrderStatus.COMPLETED);
        orderDAO.save(order);

        // Trigger observers
        orderSubject.notifyObservers(order);

        return order;
    }

    public List<Order> getAllOrders() { return orderDAO.findAll(); }
}