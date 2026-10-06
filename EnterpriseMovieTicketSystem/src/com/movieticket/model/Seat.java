package com.movieticket.model;

import com.movieticket.enums.SeatType;

public class Seat {
    private final String seatId;
    private final int row;
    private final int column;
    private final SeatType seatType;
    private final double price;

    public Seat(String seatId, int row, int column, SeatType seatType, double price) {
        this.seatId = seatId;
        this.row = row;
        this.column = column;
        this.seatType = seatType;
        this.price = price;
    }

    public String getSeatId() { return seatId; }
    public SeatType getSeatType() { return seatType; }
    public double getPrice() { return price; }
}