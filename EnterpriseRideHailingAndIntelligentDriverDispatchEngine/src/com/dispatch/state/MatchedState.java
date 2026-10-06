package com.dispatch.state;

import com.dispatch.enums.DriverStatus;
import com.dispatch.model.Driver;
import com.dispatch.model.Ride;

public class MatchedState implements RideState {

	public void handleMatch(Ride ride, Driver driver) {
		System.err.println("Illegal Transition:Driver already assigned.");
	}

	public void handleStart(Ride ride) {
		ride.getDriver().setStatus(DriverStatus.IN_TRANSIT);
		ride.setState(new TransitState());
		System.out.println("->[STATE]Ride" + ride.getRideId() + "->IN_TRANSIT.");

	}

	public void handleComplete(Ride ride) {
		System.err.println("Illegal Transition :Must start ride before completing.");
	}

	public void handleCancel(Ride ride) {
		ride.getDriver().setStatus(DriverStatus.AVAILABLE);
		ride.getRider().setActiveRide(false);
		ride.setState(new CancelledState());

		System.out.println("->Ride:" + ride.getRideId() + "->Cancelled by user.Driver released.");
	}
}