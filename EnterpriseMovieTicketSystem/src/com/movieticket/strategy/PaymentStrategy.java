package com.movieticket.strategy;

public interface PaymentStrategy {
    boolean processPayment(String bookingId, double amount);
    boolean processRefund(String bookingId, double amount);
}