package com.movieticket.strategy;

public class CardPaymentStrategy implements PaymentStrategy {
    @Override
    public boolean processPayment(String bookingId, double amount) {
        System.out.println("[CARD GATEWAY] Processed payment of ₹" + amount + " for Booking: " + bookingId);
        return true;
    }
    @Override
    public boolean processRefund(String bookingId, double amount) {
        System.out.println("[CARD GATEWAY] Processed refund of ₹" + amount + " for Booking: " + bookingId);
        return true;
    }
}