package com.dispatch.model;

public class Location {
	private final double latitude;
	private final double longitude;
	public Location(double latitude, double longitude) {
		this.latitude=latitude;
		this.longitude=longitude;
	}
	public double distanceTo(Location other) {
		if(other==null)return 0.0;
		double latDiff=Math.toRadians(other.latitude-this.latitude);
		double lonDiff=Math.toRadians(other.longitude-this.longitude);
		 double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2)
	                + Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(other.latitude))
	                * Math.sin(lonDiff / 2) * Math.sin(lonDiff / 2);
	        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	        return 6371.0 * c; // Earth radius ~6371 km
	    }

	    public double getLatitude() { return latitude; }
	    public double getLongitude() { return longitude; }

	    @Override
	    public String toString() {
	        return String.format("(%.4f, %.4f)", latitude, longitude);
	    }
	}