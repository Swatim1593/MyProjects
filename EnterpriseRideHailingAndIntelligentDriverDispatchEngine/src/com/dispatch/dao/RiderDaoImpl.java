package com.dispatch.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.dispatch.model.Rider;

public class RiderDaoImpl implements GenericDao<Rider> {
	private final Map<String, Rider> riderStore = new ConcurrentHashMap<>();

	@Override
	public void save(Rider entity) {
		riderStore.put(entity.getId(), entity);
	}

	@Override
	public Optional<Rider> findById(String id) {
		return Optional.ofNullable(riderStore.get(id));
	}

	@Override
	public List<Rider> findAll() {
		return new ArrayList<>(riderStore.values());
	}

	@Override
	public void delete(String id) {
		riderStore.remove(id);
	}
}