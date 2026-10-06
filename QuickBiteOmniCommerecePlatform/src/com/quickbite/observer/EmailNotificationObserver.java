package com.quickbite.observer;

import com.quickbite.entity.Order;

public class EmailNotificationObserver implements OrderObserver {

	public void update(Order order) {
		System.out.println(" [EMAIL NOTIFICATION] Dispatched email to " + order.getCustomer().getEmail()
				+ " for Order #" + order.getOrderId());
	}
}