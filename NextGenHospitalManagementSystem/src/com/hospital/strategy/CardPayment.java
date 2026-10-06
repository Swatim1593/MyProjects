package com.hospital.strategy;

public class CardPayment implements PaymentStrategy {
    private String cardNumber;

    public CardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean processPayment(double amount) {
        System.out.println("  [PAYMENT] Processing ₹" + amount + " via Card ending with .." + cardNumber.substring(cardNumber.length() - 4));
        return true;
    }
}
