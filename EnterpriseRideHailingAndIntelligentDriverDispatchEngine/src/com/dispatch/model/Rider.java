package com.dispatch.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Rider extends BaseEntity {
    private Location location;
    private boolean activeRide;
    // One-to-Many Relationship: Rider has many historical rides
    private final List<Ride> tripHistory;

    public Rider(String id, String name, Location location) {
        super(id, name);
        this.location = location;
        this.activeRide = false;
        this.tripHistory = new ArrayList<>();
    }

    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }
    public boolean hasActiveRide() { return activeRide; }
    public void setActiveRide(boolean activeRide) { this.activeRide = activeRide; }

    public void addTripToHistory(Ride ride) {
        this.tripHistory.add(ride);
    }

    public List<Ride> getTripHistory() {
        return Collections.unmodifiableList(tripHistory);
    }
}