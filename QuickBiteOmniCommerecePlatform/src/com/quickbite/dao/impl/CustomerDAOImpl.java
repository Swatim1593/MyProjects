package com.quickbite.dao.impl;

import com.quickbite.dao.GenericDAO;
import com.quickbite.entity.Customer;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CustomerDAOImpl implements GenericDAO<Customer> {
    private Map<Integer, Customer> customerMap = new ConcurrentHashMap<>();

    @Override
    public void save(Customer entity) { customerMap.put(entity.getUserId(), entity); }

    @Override
    public void update(Customer entity) { customerMap.put(entity.getUserId(), entity); }

    @Override
    public void delete(int id) { customerMap.remove(id); }

    @Override
    public Customer findById(int id) { return customerMap.get(id); }

    @Override
    public List<Customer> findAll() { return new ArrayList<>(customerMap.values()); }
}