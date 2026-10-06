package com.hospital.strategy;

public class CashPayment implements PaymentStrategy {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("  [PAYMENT] Received ₹" + amount + " via Cash.");
        return true;
    }
}



