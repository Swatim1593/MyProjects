package com.movieticket.decorator;

public class PopcornComboDecorator extends TicketDecorator {
    public PopcornComboDecorator(TicketComponent ticket) {
        super(ticket);
    }

    @Override
    public double getCost() {
        return wrappedTicket.getCost() + 150.0;
    }

    @Override
    public String getDescription() {
        return wrappedTicket.getDescription() + " + Popcorn Combo [₹150]";
    }
}