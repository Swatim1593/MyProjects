package com.movieticket.strategy;

public class NetBankingPaymentStrategy implements PaymentStrategy {
    @Override
    public boolean processPayment(String bookingId, double amount) {
        System.out.println("[NETBANKING GATEWAY] Processed payment of ₹" + amount + " for Booking: " + bookingId);
        return true;
    }
    @Override
    public boolean processRefund(String bookingId, double amount) {
        System.out.println("[NETBANKING GATEWAY] Processed refund of ₹" + amount + " for Booking: " + bookingId);
        return true;
    }
}