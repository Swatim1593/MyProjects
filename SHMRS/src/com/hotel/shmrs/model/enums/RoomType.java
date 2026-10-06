package com.hotel.shmrs.model.enums;

public enum RoomType {
    DELUXE("Deluxe Room", 3500.0, 0.05),
    PREMIUM("Premium Room", 5500.0, 0.10),
    SUITE("Executive Suite", 9500.0, 0.15);

    private final String displayName;
    private final double baseRate;
    private final double taxRate;

    RoomType(String displayName, double baseRate, double taxRate) {
        this.displayName = displayName;
        this.baseRate = baseRate;
        this.taxRate = taxRate;
    }

    public String getDisplayName() { return displayName; }
    public double getBaseRate() { return baseRate; }
    public double getTaxRate() { return taxRate; }
}