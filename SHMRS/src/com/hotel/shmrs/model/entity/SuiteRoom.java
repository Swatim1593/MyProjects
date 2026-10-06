package com.hotel.shmrs.model.entity;

import com.hotel.shmrs.model.enums.RoomType;

public class SuiteRoom extends Room {
    public SuiteRoom(int roomNumber) {
        super(roomNumber, RoomType.SUITE);
    }

    @Override
    public double calculatePrice(int nights) {
        return (roomType.getBaseRate() * nights * (1 + roomType.getTaxRate())) + 1000.0; // Extra Butler surcharge
    }
}