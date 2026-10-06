package com.hospital.strategy;

public class UpiPayment implements PaymentStrategy {
    private String upiId;

    public UpiPayment(String upiId) {
        this.upiId = upiId;
    }

    @Override
    public boolean processPayment(double amount) {
        System.out.println("  [PAYMENT] Debiting ₹" + amount + " from VPA: " + upiId + " via UPI Switch.");
        return true;
    }
}