package com.hotel.shmrs.strategy;

import com.hotel.shmrs.exceptions.InvalidPaymentException;
import com.hotel.shmrs.model.enums.PaymentMode;

public class CardPaymentStrategy implements PaymentStrategy {
    private final String cardNumber;
    private final PaymentMode mode;

    public CardPaymentStrategy(String cardNumber, PaymentMode mode) {
        this.cardNumber = cardNumber;
        this.mode = mode;
    }

    @Override
    public boolean executePayment(double amount) throws InvalidPaymentException {
        if (amount <= 0) {
            throw new InvalidPaymentException("Payment value must be strictly positive.");
        }
        System.out.println("[Payment: " + mode + "] Charged ₹" + amount + " to Card ****-****-****-" + 
                cardNumber.substring(Math.max(0, cardNumber.length() - 4)));
        return true;
    }

    @Override
    public PaymentMode getMode() {
        return mode;
    }
}