package com.movieticket.decorator;

public class Glasses3DDecorator extends TicketDecorator {
    public Glasses3DDecorator(TicketComponent ticket) {
        super(ticket);
    }

    @Override
    public double getCost() {
        return wrappedTicket.getCost() + 30.0;
    }

    @Override
    public String getDescription() {
        return wrappedTicket.getDescription() + " + 3D Glasses Rental [₹30]";
    }
}