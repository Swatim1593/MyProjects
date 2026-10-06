package com.hotel.shmrs.model.entity;

import java.io.Serializable;

public class HotelService implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String serviceName;
    private final double cost;

    public HotelService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() { return serviceName; }
    public double getCost() { return cost; }

    @Override
    public String toString() {
        return serviceName + " (₹" + cost + ")";
    }
}