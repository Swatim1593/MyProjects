package com.dispatch.stretegy;
import com.dispatch.model.Ride;

public class StandardPricingStrategy  implements PricingStrategy{
	@Override
	public double calculateFare(Ride ride, double distanceKm) {
		ride.setSurgeMultiplier(1.0);
		return
				ride.getRideType().getBasePrice()+(distanceKm*ride.getRideType().getPerKmPrice());
	}
	

}
