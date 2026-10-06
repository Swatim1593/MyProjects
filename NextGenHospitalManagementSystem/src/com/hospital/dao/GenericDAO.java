package com.hospital.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDAO<T, K> {
    void save(T entity);
    Optional<T> findById(K id);
    List<T> findAll();
    void update(T entity);
    void delete(K id);
}