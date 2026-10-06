package com.dispatch.enums;

public enum RideType {
	AUTO(30.0,10.0),
	SEDAN(50.0,15.0),
	SUV(80.0,22.0);
	private final double basePrice;
	private final double perKmPrice;
	RideType(double basePrice,double perKnPrice){
		this.basePrice=basePrice;
		this.perKmPrice=perKnPrice;
	}
	public double getBasePrice() {
		return basePrice;
	}
	public double getPerKmPrice() {
		return perKmPrice;
	}
	

}
