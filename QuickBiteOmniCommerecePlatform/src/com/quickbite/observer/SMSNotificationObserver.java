package com.quickbite.observer;

import com.quickbite.entity.Order;

public class SMSNotificationObserver implements OrderObserver {

	public void update(Order order) {
		System.out.println("  [SMS NOTIFICATION] Sent SMS alert to " + order.getCustomer().getName() + " for Order #"
				+ order.getOrderId());
	}
}