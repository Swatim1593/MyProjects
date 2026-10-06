package com.dispatch.decorator;

public class RideOptionDecorator implements RideComponent {

	protected final RideComponent decoratedRide;

	public RideOptionDecorator(RideComponent decoratedRide) {
		super();
		this.decoratedRide = decoratedRide;
	}

	@Override
	public double getCost() {
		// TODO Auto-generated method stub
		return decoratedRide.getCost();
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return decoratedRide.getDescription();
	}

}