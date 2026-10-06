package com.dispatch.decorator;

public class BaseRideBill implements RideComponent {
    private final String rideId;
    private final double baseFare;

    public BaseRideBill(String rideId, double baseFare) {
        this.rideId = rideId;
        this.baseFare = baseFare;
    }

    @Override
    public double getCost() {
        return baseFare;
    }

    @Override
    public String getDescription() {
        return "Base Fare [" + rideId + "]";
    }
}