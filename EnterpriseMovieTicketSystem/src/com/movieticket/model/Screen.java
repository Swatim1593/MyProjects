package com.movieticket.model;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Screen {
    private final String id;
    private final String name;
    private final Map<String, Seat> seats;

    public Screen(String id, String name, List<Seat> seatList) {
        this.id = id;
        this.name = name;
        this.seats = new ConcurrentHashMap<>();
        seatList.forEach(seat -> this.seats.put(seat.getSeatId(), seat));
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public Map<String, Seat> getSeats() { return seats; }
}