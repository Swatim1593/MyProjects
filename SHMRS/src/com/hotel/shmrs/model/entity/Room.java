package com.hotel.shmrs.model.entity;

import com.hotel.shmrs.model.enums.RoomType;
import java.io.Serializable;

public abstract class Room implements Serializable, Comparable<Room> {
    private static final long serialVersionUID = 1L;

    protected final int roomNumber;
    protected final RoomType roomType;
    protected volatile boolean isAvailable;

    public Room(int roomNumber, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.isAvailable = true;
    }

    public int getRoomNumber() { return roomNumber; }
    public RoomType getRoomType() { return roomType; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { this.isAvailable = available; }

    public abstract double calculatePrice(int nights);

    @Override
    public int compareTo(Room other) {
        return Integer.compare(this.roomNumber, other.roomNumber);
    }

    @Override
    public String toString() {
        return "[" + roomType.getDisplayName() + " #" + roomNumber + " | Base: ₹" + roomType.getBaseRate() + " | Available=" + isAvailable + "]";
    }
}