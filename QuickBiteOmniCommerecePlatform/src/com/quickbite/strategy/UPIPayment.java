package com.quickbite.strategy;

public class UPIPayment extends Payment {
    private String upiId;

    public UPIPayment(String paymentId, double amount, String upiId) {
        super(paymentId, amount);
        this.upiId = upiId;
    }

    @Override
    public boolean processPayment() {
        System.out.println("  [UPI PAYMENT] Debiting ₹" + amount + " from VPA '" + upiId + "' via NPCI switch.");
        return true;
    }
}