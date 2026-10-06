package com.movieticket.decorator;

public class BaseTicket implements TicketComponent {
    private final String seatInfo;
    private final double price;

    public BaseTicket(String seatInfo, double price) {
        this.seatInfo = seatInfo;
        this.price = price;
    }

    @Override
    public double getCost() {
        return price;
    }

    @Override
    public String getDescription() {
        return "Base Seats " + seatInfo + " [₹" + price + "]";
    }
}