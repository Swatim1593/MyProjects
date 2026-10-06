package com.quickbite.facade;
import com.quickbite.decorator.CustomerLoyaltyDecorator;
import com.quickbite.entity.*;
import com.quickbite.factory.PaymentFactory;
import com.quickbite.service.*;
import com.quickbite.strategy.Payment;
import java.util.List;

public class OrderFacade {
 private CustomerService customerService;
 private ProductService productService;
 private CartService cartService;
 private OrderService orderService;

 public OrderFacade(CustomerService customerService, ProductService productService, CartService cartService, OrderService orderService) {
     this.customerService = customerService;
     this.productService = productService;
     this.cartService = cartService;
     this.orderService = orderService;
 }

 public Order checkout(int orderId, int customerId, String paymentType, String paymentCredential) {
     Customer customer = customerService.getCustomer(customerId);
     List<OrderItem> items = cartService.getCartItems();
     double total = cartService.getCartTotal();

     // Apply loyalty decorator
     CustomerLoyaltyDecorator decorator = new CustomerLoyaltyDecorator(customer);
     double finalAmount = decorator.calculateDiscount(total);
     System.out.println("Final amount:"+finalAmount);

     // Factory creates payment instance
     Payment payment = PaymentFactory.createPayment(paymentType, "PAY-" + System.currentTimeMillis(), finalAmount, paymentCredential);

     // Create order via order service
     Order order = orderService.createOrder(orderId, customer, items, payment,finalAmount);
     cartService.clearCart();

     return order;
 }
}

