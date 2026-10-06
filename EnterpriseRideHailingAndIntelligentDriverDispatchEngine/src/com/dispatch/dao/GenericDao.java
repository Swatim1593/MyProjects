package com.dispatch.dao;

import java.util.List;
import java.util.Optional;

import com.dispatch.model.BaseEntity;

public interface GenericDao<T extends BaseEntity> {
	void save(T entity);

	Optional<T> findById(String id);

	List<T> findAll();

	void delete(String id);
}