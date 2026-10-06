package com.hotel.shmrs.model.entity;

import com.hotel.shmrs.model.enums.RoomType;

public class DeluxeRoom extends Room {
    public DeluxeRoom(int roomNumber) {
        super(roomNumber, RoomType.DELUXE);
    }

    @Override
    public double calculatePrice(int nights) {
        return roomType.getBaseRate() * nights * (1 + roomType.getTaxRate());
    }
}