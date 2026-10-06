package com.dispatch.decorator;

public class ChildSeatDecorator extends RideOptionDecorator {

	private static final double CHILD_SEAT_PRICE = 75.0;

	public ChildSeatDecorator(RideComponent decoratedRide) {
		super(decoratedRide);
	}

	public double getCost() {
		return super.getCost() + CHILD_SEAT_PRICE;
	}

	public String getDescription() {
		return super.getDescription() + "+Child Safety Seat(Rs.75)";
	}

}