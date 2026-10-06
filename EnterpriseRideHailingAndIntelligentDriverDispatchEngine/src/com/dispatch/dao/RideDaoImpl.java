package com.dispatch.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.dispatch.model.Ride;

public class RideDaoImpl {
	private final Map<String, Ride> rideStore = new ConcurrentHashMap<>();

	public void save(Ride ride) {
		rideStore.put(ride.getRideId(), ride);
	}

	public Optional<Ride> findById(String id) {
		return Optional.ofNullable(rideStore.get(id));
	}

	public List<Ride> findAll() {
		return new ArrayList<>(rideStore.values());
	}
}