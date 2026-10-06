package com.quickbite.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.quickbite.dao.GenericDAO;
import com.quickbite.entity.Order;

public class OrderDAOImpl implements GenericDAO<Order> {
	private Map<Integer, Order> orderMap = new ConcurrentHashMap<>();

	public void save(Order entity) {
		orderMap.put(entity.getOrderId(), entity);
	}

	public void update(Order entity) {
		orderMap.put(entity.getOrderId(), entity);
	}

	public void delete(int id) {
		orderMap.remove(id);
	}

	public Order findById(int id) {
		return orderMap.get(id);
	}

	public List<Order> findAll() {
		return new ArrayList<>(orderMap.values());
	}
}
