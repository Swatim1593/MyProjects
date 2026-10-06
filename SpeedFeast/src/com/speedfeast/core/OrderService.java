package com.speedfeast.core;
public class OrderService {
    private Payment payment; // Loose coupling interface boundary

    // Dependency Injection via constructor parameter pass-in
    public OrderService(Payment payment) {
        this.payment = payment;
    }

	/*
	 * checkoutCart[0] = new FoodItem(401, "Hyderabadi Biryani", 290.0);
	 * checkoutCart[1] = new FoodItem(402, "Paneer Tikka Pizza", 340.0);
	 * checkoutCart[2] = new FoodItem(403, "Crunchy Veg Burger", 120.0);
	 */
    public Order placeOrder(Customer customer, FoodItem[] cart) {
        CartService cartService = new CartService();
        double total = cartService.calculateBill(cart);

        // Apply business matrix logic for prime tier benefits
        
        if (!customer.isPrimeMember()) {
            total += 40.0;   // Flat standard handling charge
            System.out.println("[BILLING] Standard Account detected. Convenience charge applied: ₹40");
        } else {
            System.out.println("[BILLING] Active Prime Tier recognized. Wave delivery processing fees.");
        }

        boolean paymentStatus = payment.pay(total, customer);

        if (paymentStatus) {
            return new Order(total, OrderStatus.PLACED);
        } else {
            return new Order(total, OrderStatus.PAYMENT_FAILED);
        }
    }
}