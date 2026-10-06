package com.dispatch.state;

import com.dispatch.enums.DriverStatus;
import com.dispatch.model.Driver;
import com.dispatch.model.Ride;

public class TransitState implements RideState {
	@Override
	public void handleMatch(Ride ride, Driver driver) {
		System.err.println("Ride in transit.");
	}

	@Override
	public void handleStart(Ride ride) {
		System.err.println("Trip already in transit.");
	}

	@Override
	public void handleComplete(Ride ride) {
		double driverShare = ride.getFare() * 0.80; // 80% Payout
		Driver driver = ride.getDriver();
		driver.addEarnings(driverShare);
		driver.recordCompletedTrip(ride);
		driver.setStatus(DriverStatus.AVAILABLE);
		driver.setLocation(ride.getDropoff());

		ride.getRider().setLocation(ride.getDropoff());
		ride.getRider().setActiveRide(false);
		ride.getRider().addTripToHistory(ride);

		ride.setState(new CompletedState());
		System.out.println("-> [STATE] Ride " + ride.getRideId() + " -> COMPLETED. Driver Payout: Rs." + driverShare);
	}

	@Override
	public void handleCancel(Ride ride) {
		System.err.println("Illegal Transition: Cannot cancel ongoing in-transit trip.");
	}
}