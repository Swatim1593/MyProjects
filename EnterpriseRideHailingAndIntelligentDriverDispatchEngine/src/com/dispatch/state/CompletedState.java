package com.dispatch.state;

import com.dispatch.model.Driver;
import com.dispatch.model.Ride;

public class CompletedState implements RideState {
	@Override
	public void handleMatch(Ride ride, Driver driver) {
	}

	@Override
	public void handleStart(Ride ride) {
	}

	@Override
	public void handleComplete(Ride ride) {
	}

	@Override
	public void handleCancel(Ride ride) {
	}
}