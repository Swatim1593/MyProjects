package com.dispatch.state;

import com.dispatch.enums.DriverStatus;
import com.dispatch.model.Driver;
import com.dispatch.model.Ride;

public class RequestedState implements RideState {
    @Override
    public void handleMatch(Ride ride, Driver driver) {
        ride.setDriver(driver);
        driver.setStatus(DriverStatus.MATCHED);
        ride.setState(new MatchedState());
        System.out.println("-> [STATE] Ride " + ride.getRideId() + " -> MATCHED with Driver " + driver.getName());
    }

    @Override
    public void handleStart(Ride ride) {
        System.err.println("Illegal Transition: Cannot start a ride without a matched driver.");
    }

    @Override
    public void handleComplete(Ride ride) {
        System.err.println("Illegal Transition: Cannot complete an unassigned ride.");
    }

    @Override
    public void handleCancel(Ride ride) {
        ride.setState(new CancelledState());
        ride.getRider().setActiveRide(false);
        System.out.println("-> [STATE] Ride " + ride.getRideId() + " -> CANCELLED while searching.");
    }
}