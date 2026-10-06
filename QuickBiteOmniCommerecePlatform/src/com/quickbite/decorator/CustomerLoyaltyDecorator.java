package com.quickbite.decorator;


import com.quickbite.entity.Customer;

public class CustomerLoyaltyDecorator {
	private Customer customer;

	public CustomerLoyaltyDecorator(Customer customer) {
		this.customer = customer;
	}

	public double calculateDiscount(double originalAmount) {
		System.out.println(originalAmount);
		if (customer.isPremium()) {
			System.out.println(originalAmount);
			System.out.println("  [DECORATOR] Applied 15% Premium Loyalty Discount for " + customer.getName());
			return (originalAmount * 0.85);
		}
		return originalAmount;
	}
}