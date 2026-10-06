package com.dispatch.model;

import com.dispatch.enums.RideType;
import com.dispatch.state.RequestedState;
import com.dispatch.state.RideState;

public class Ride {
	private final String rideId;
	// Association: Many-to-One
	private final Rider rider;
	// Association: Many-to-One
	private Driver driver;
	// Association: One-to-One
	private Payment payment;

	private final Location pickup;
	private final Location dropoff;
	private final RideType rideType;
	private double fare;
	private double surgeMultiplier;
	private RideState state;

	public Ride(String rideId, Rider rider, Location pickup, Location dropoff, RideType rideType) {
		this.rideId = rideId;
		this.rider = rider;
		this.pickup = pickup;
		this.dropoff = dropoff;
		this.rideType = rideType;
		this.surgeMultiplier = 1.0;
		this.state = new RequestedState();
	}

	public void proceedToMatch(Driver matchedDriver) {
		this.state.handleMatch(this, matchedDriver);
	}

	public void startTrip() {
		this.state.handleStart(this);
	}

	public void completeTrip() {
		this.state.handleComplete(this);
	}

	public void cancelTrip() {
		this.state.handleCancel(this);
	}

	public String getRideId() {
		return rideId;
	}

	public Rider getRider() {
		return rider;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Location getPickup() {
		return pickup;
	}

	public Location getDropoff() {
		return dropoff;
	}

	public RideType getRideType() {
		return rideType;
	}

	public double getFare() {
		return fare;
	}

	public void setFare(double fare) {
		this.fare = fare;
	}

	public double getSurgeMultiplier() {
		return surgeMultiplier;
	}

	public void setSurgeMultiplier(double surgeMultiplier) {
		this.surgeMultiplier = surgeMultiplier;
	}

	public RideState getState() {
		return state;
	}

	public void setState(RideState state) {
		this.state = state;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Ride Details: [ID=").append(rideId).append(", Rider=").append(rider.getName()).append(", Driver=")
				.append(driver != null ? driver.getName() : "NONE").append(", Type=").append(rideType)
				.append(", Total Fare=").append(fare).append(", Status=").append(state.getClass().getSimpleName())
				.append("]");
		return sb.toString();
	}
}