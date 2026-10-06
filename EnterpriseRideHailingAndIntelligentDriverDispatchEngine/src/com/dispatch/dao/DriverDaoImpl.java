package com.dispatch.dao;

import com.dispatch.model.Driver;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class DriverDaoImpl implements GenericDao<Driver> {
    private final Map<String, Driver> driverStore = new ConcurrentHashMap<>();

    @Override
    public void save(Driver entity) {
        driverStore.put(entity.getId(), entity);
    }

    @Override
    public Optional<Driver> findById(String id) {
        return Optional.ofNullable(driverStore.get(id));
    }

    @Override
    public List<Driver> findAll() {
        return new ArrayList<>(driverStore.values());
    }

    @Override
    public void delete(String id) {
        driverStore.remove(id);
    }
}