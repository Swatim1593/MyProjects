package com.dispatch.decorator;

public class ExtraLuggageDecorator extends RideOptionDecorator {

	private static final double LUGGAGE_PRICE = 50.0;

	public ExtraLuggageDecorator(RideComponent decoratedRide) {
		super(decoratedRide);
	}

	public double getCost() {
		return super.getCost() + LUGGAGE_PRICE;
	}

	public String getDescription() {
		return super.getDescription() + "+Extra Lugage Carrier(Rs.50)";
	}

}