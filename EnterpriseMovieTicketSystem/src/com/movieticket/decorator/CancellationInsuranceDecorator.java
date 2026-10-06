package com.movieticket.decorator;

public class CancellationInsuranceDecorator extends TicketDecorator {
    public CancellationInsuranceDecorator(TicketComponent ticket) {
        super(ticket);
    }

    @Override
    public double getCost() {
        return wrappedTicket.getCost() + 20.0;
    }

    @Override
    public String getDescription() {
        return wrappedTicket.getDescription() + " + Ticket Insurance [₹20]";
    }
}