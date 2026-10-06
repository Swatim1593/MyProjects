package com.quickbite.controller;

import com.quickbite.entity.Order;
import com.quickbite.facade.OrderFacade;

public class OrderController {
 private OrderFacade orderFacade;

 public OrderController(OrderFacade orderFacade) { this.orderFacade = orderFacade; }

 public Order processCheckout(int orderId, int customerId, String paymentType, String credential) {
     return orderFacade.checkout(orderId, customerId, paymentType, credential);
 }
}
