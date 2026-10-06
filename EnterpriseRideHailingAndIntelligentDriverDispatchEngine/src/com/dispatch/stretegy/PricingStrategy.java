package com.dispatch.stretegy;

import com.dispatch.model.Ride;

public interface PricingStrategy {

	double calculateFare(Ride ride, double distanceKm);
}