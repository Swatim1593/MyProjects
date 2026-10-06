package com.quickbite.strategy;

public class PaypalSDK {
    public int executeCharge(double amountInINR, String token) {
        System.out.println("  [PAYPAL ADAPTER] Bridging charge to External PayPal API (Token: " + token + ") for ₹" + amountInINR);
        return 200; // Success Code
    }
}

