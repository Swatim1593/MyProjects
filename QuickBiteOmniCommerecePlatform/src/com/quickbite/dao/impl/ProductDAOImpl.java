package com.quickbite.dao.impl;

import com.quickbite.dao.GenericDAO;
import com.quickbite.entity.Product;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ProductDAOImpl implements GenericDAO<Product> {
    private Map<Integer, Product> productMap = new ConcurrentHashMap<>();

    @Override
    public void save(Product entity) { productMap.put(entity.getProductId(), entity); }

    @Override
    public void update(Product entity) { productMap.put(entity.getProductId(), entity); }

    @Override
    public void delete(int id) { productMap.remove(id); }

    @Override
    public Product findById(int id) { return productMap.get(id); }

    @Override
    public List<Product> findAll() { return new ArrayList<>(productMap.values()); }
}