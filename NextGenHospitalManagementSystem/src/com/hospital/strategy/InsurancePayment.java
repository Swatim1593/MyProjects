package com.hospital.strategy;

public class InsurancePayment implements PaymentStrategy {
    private String policyNo;

    public InsurancePayment(String policyNo) {
        this.policyNo = policyNo;
    }

    @Override
    public boolean processPayment(double amount) {
        System.out.println("  [PAYMENT] Submitting claim for ₹" + amount + " against Insurance Policy: " + policyNo);
        return true;
    }
}