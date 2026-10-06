package com.hotel.shmrs.strategy;

import com.hotel.shmrs.exceptions.InvalidPaymentException;
import com.hotel.shmrs.model.enums.PaymentMode;

public interface PaymentStrategy {
    boolean executePayment(double amount) throws InvalidPaymentException;
    PaymentMode getMode();
}