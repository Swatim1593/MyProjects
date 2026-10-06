package com.dispatch.state;

import com.dispatch.model.Driver;
import com.dispatch.model.Ride;

public class CancelledState implements RideState {
	@Override
	public void handleMatch(Ride ride, Driver driver) {
		System.err.println("Ride is cancelled.");
	}

	@Override
	public void handleStart(Ride ride) {
		System.err.println("Ride is cancelled.");
	}

	@Override
	public void handleComplete(Ride ride) {
		System.err.println("Ride is cancelled.");
	}

	@Override
	public void handleCancel(Ride ride) {
		System.err.println("Ride is already cancelled.");
	}
}