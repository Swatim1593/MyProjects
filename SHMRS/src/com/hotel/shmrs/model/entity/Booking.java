package com.hotel.shmrs.model.entity;

import com.hotel.shmrs.model.enums.BookingStatus;
import com.hotel.shmrs.model.enums.PaymentMode;
import java.io.Serializable;

public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String bookingId;
    private final Customer customer;
    private final Room room;
    private final int nights;
    private final double totalCost;
    private final PaymentMode paymentMode;
    private BookingStatus status;

    public Booking(String bookingId, Customer customer, Room room, int nights,
                   double totalCost, PaymentMode paymentMode, BookingStatus status) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.room = room;
        this.nights = nights;
        this.totalCost = totalCost;
        this.paymentMode = paymentMode;
        this.status = status;
    }

    public String getBookingId() { return bookingId; }
    public Customer getCustomer() { return customer; }
    public Room getRoom() { return room; }
    public int getNights() { return nights; }
    public double getTotalCost() { return totalCost; }
    public PaymentMode getPaymentMode() { return paymentMode; }
    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }

    @Override
    public String toString() {
        return "Booking #" + bookingId + " [" + status + "] | " + customer.getName() + " | Room #" + 
               room.getRoomNumber() + " (" + room.getRoomType().getDisplayName() + ") | " + nights + " Nights | Total: ₹" + totalCost;
    }
}