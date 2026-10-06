package com.movieticket.model;

import java.time.LocalDateTime;
import java.util.List;

public class Ticket {
    private final String ticketId;
    private final String bookingId;
    private final String movieTitle;
    private final String theatreName;
    private final List<String> seatIds;
    private final LocalDateTime showTime;

    public Ticket(String ticketId, String bookingId, String movieTitle, String theatreName, List<String> seatIds, LocalDateTime showTime) {
        this.ticketId = ticketId;
        this.bookingId = bookingId;
        this.movieTitle = movieTitle;
        this.theatreName = theatreName;
        this.seatIds = seatIds;
        this.showTime = showTime;
    }

    public String getTicketId() { return ticketId; }

    @Override
    public String toString() {
        return "E-TICKET [TicketID=" + ticketId + ", BookingID=" + bookingId + ", Movie=" + movieTitle +
               ", Theatre=" + theatreName + ", Seats=" + seatIds + ", ShowTime=" + showTime + "]";
    }
}