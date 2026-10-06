package com.quickbite.service;

import com.quickbite.collections.MaxHeap;
import com.quickbite.dao.impl.OrderDAOImpl;
import com.quickbite.entity.Order;
import com.quickbite.entity.OrderItem;
import com.quickbite.entity.OrderStatus;
import com.quickbite.entity.Product;
import java.util.*;
import java.util.stream.Collectors;

public class ReportService {
 private OrderDAOImpl orderDAO;

 public ReportService(OrderDAOImpl orderDAO) { this.orderDAO = orderDAO; }

 // Java 8 Streams: Total Revenue
 public double calculateTotalRevenue() {
     return orderDAO.findAll().stream()
             .filter(o -> o.getStatus() == OrderStatus.COMPLETED)
             .mapToDouble(Order::getTotalAmount)
             .reduce(0.0, Double::sum);
 }

 // Java 8 Streams: Category Count Report
 public Map<String, Long> getCategoryCountReport() {
     return orderDAO.findAll().stream()
             .flatMap(o -> o.getItems().stream())
             .collect(Collectors.groupingBy(
                     item -> item.getProduct().getCategory().getCategoryName(),
                     Collectors.counting()
             ));
 }

 // Custom Heap Data Structure: Top Selling Product
 public Product getTopSellingProduct() {
     Map<Product, Integer> salesMap = new HashMap<>();
     for (Order o : orderDAO.findAll()) {
         for (OrderItem item : o.getItems()) {
             salesMap.put(item.getProduct(), salesMap.getOrDefault(item.getProduct(), 0) + item.getQuantity());
         }
     }

     MaxHeap maxHeap = new MaxHeap();
     salesMap.forEach(maxHeap::insert);

     MaxHeap.ProductEntry maxEntry = maxHeap.extractMax();
     return maxEntry != null ? maxEntry.product : null;
 }
}