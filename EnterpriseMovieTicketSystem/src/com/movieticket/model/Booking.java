package com.movieticket.model;

import com.movieticket.enums.BookingStatus;
import java.util.List;

public class Booking {
    private final String bookingId;
    private final Customer customer;
    private final Show show;
    private final List<ShowSeat> bookedSeats;
    private double totalAmount;
    private BookingStatus status;
    private Ticket ticket;
    private Payment payment;

    public Booking(String bookingId, Customer customer, Show show, List<ShowSeat> bookedSeats, double totalAmount) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.show = show;
        this.bookedSeats = bookedSeats;
        this.totalAmount = totalAmount;
        this.status = BookingStatus.PENDING;
    }

    public String getBookingId() { return bookingId; }
    public Customer getCustomer() { return customer; }
    public Show getShow() { return show; }
    public List<ShowSeat> getBookedSeats() { return bookedSeats; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }
    public void setTicket(Ticket ticket) { this.ticket = ticket; }
    public void setPayment(Payment payment) { this.payment = payment; }
}