package com.hotel.shmrs.strategy;

import com.hotel.shmrs.exceptions.InvalidPaymentException;
import com.hotel.shmrs.model.enums.PaymentMode;

public class UPIPaymentStrategy implements PaymentStrategy {
    private final String upiId;

    public UPIPaymentStrategy(String upiId) {
        this.upiId = upiId;
    }

    @Override
    public boolean executePayment(double amount) throws InvalidPaymentException {
        if (amount <= 0) {
            throw new InvalidPaymentException("Payment value must be strictly positive.");
        }
        System.out.println("[Payment: UPI] Successfully debited ₹" + amount + " via VPA: " + upiId);
        return true;
    }

    @Override
    public PaymentMode getMode() {
        return PaymentMode.UPI;
    }
}