package com.dispatch.state;

import com.dispatch.model.Driver;
import com.dispatch.model.Ride;

public interface RideState {
	void handleMatch(Ride ride, Driver driver);

	void handleStart(Ride ride);

	void handleComplete(Ride ride);

	void handleCancel(Ride ride);

}