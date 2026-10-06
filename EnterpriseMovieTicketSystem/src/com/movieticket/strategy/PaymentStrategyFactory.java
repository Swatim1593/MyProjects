package com.movieticket.strategy;

import com.movieticket.enums.PaymentMethod;

public class PaymentStrategyFactory {
    public static PaymentStrategy getStrategy(PaymentMethod method) {
        switch (method) {
            case UPI: return new UPIPaymentStrategy();
            case CREDIT_CARD:
            case DEBIT_CARD: return new CardPaymentStrategy();
            case NET_BANKING: return new NetBankingPaymentStrategy();
            default: return new UPIPaymentStrategy();
        }
    }
}