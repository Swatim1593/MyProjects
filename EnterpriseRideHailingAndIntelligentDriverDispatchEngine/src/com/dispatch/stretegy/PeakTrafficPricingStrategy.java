package com.dispatch.stretegy;

import com.dispatch.model.Ride;

public class PeakTrafficPricingStrategy implements PricingStrategy {
    private static final double TRAFFIC_MULTIPLIER = 2.4;

    @Override
    public double calculateFare(Ride ride, double distanceKm) {
        ride.setSurgeMultiplier(TRAFFIC_MULTIPLIER);
        double base = ride.getRideType().getBasePrice() + (distanceKm * ride.getRideType().getPerKmPrice());
        return base * TRAFFIC_MULTIPLIER;
    }
}