package com.speedfeast.core;

public class Order {
	private double amount;
	private OrderStatus status;
	public Order (double amount, OrderStatus status) {
		this.amount=amount;
		this.status=status;
	}
	public double getAmount() {
		return amount;
		}
	public OrderStatus getstatus() {
		return status;
	
	
	
	}

}
