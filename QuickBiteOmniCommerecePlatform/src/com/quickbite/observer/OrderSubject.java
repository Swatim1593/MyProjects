package com.quickbite.observer;

import java.util.ArrayList;
import java.util.List;

import com.quickbite.entity.Order;

public class OrderSubject {

	/*OrderSubject orderSubject = new OrderSubject();
	orderSubject.attach(new EmailNotificationObserver());
	orderSubject.attach(new SMSNotificationObserver());*/
	
	private List<OrderObserver> observers = new ArrayList<>();

	public void attach(OrderObserver observer) {
		observers.add(observer);
	}

	public void notifyObservers(Order order) {
		for (OrderObserver observer : observers) {
			observer.update(order);
		}
	}
}
