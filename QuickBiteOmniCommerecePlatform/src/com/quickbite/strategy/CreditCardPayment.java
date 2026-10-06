package com.quickbite.strategy;

public class CreditCardPayment extends Payment {
    private String cardNumber;

    public CreditCardPayment(String paymentId, double amount, String cardNumber) {
        super(paymentId, amount);
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean processPayment() {
        System.out.println("  [CARD PAYMENT] Authorizing ₹" + amount + " via Card .." + cardNumber.substring(cardNumber.length() - 4));
        return true;
    }
}
