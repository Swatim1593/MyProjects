package com.quickbite.observer;

import com.quickbite.entity.Order;

public interface OrderObserver {
	void update(Order order);

}