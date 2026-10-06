package com.hotel.shmrs.factory;

import com.hotel.shmrs.model.entity.*;
import com.hotel.shmrs.model.enums.RoomType;

public class RoomFactory {
    public static Room createRoom(RoomType type, int roomNumber) {
        return switch (type) {
            case DELUXE -> new DeluxeRoom(roomNumber);
            case PREMIUM -> new PremiumRoom(roomNumber);
            case SUITE -> new SuiteRoom(roomNumber);
        };
    }
}