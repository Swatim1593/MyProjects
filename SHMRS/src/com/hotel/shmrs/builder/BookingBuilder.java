package com.hotel.shmrs.builder;

import com.hotel.shmrs.model.entity.Booking;
import com.hotel.shmrs.model.entity.Customer;
import com.hotel.shmrs.model.entity.Room;
import com.hotel.shmrs.model.enums.BookingStatus;
import com.hotel.shmrs.model.enums.PaymentMode;

public class BookingBuilder {
    private String bookingId;
    private Customer customer;
    private Room room;
    private int nights;
    private double totalCost;
    private PaymentMode paymentMode;
    private BookingStatus status = BookingStatus.CONFIRMED;

    public BookingBuilder setBookingId(String bookingId) { this.bookingId = bookingId; return this; }
    public BookingBuilder setCustomer(Customer customer) { this.customer = customer; return this; }
    public BookingBuilder setRoom(Room room) { this.room = room; return this; }
    public BookingBuilder setNights(int nights) { this.nights = nights; return this; }
    public BookingBuilder setTotalCost(double totalCost) { this.totalCost = totalCost; return this; }
    public BookingBuilder setPaymentMode(PaymentMode paymentMode) { this.paymentMode = paymentMode; return this; }
    public BookingBuilder setStatus(BookingStatus status) { this.status = status; return this; }

    public Booking build() {
        if (customer == null || room == null || nights <= 0) {
            throw new IllegalStateException("Cannot build incomplete booking entity.");
        }
        return new Booking(bookingId, customer, room, nights, totalCost, paymentMode, status);
    }
}