package com.quickbite.strategy;

public class PaypalAdapter extends Payment {
    private PaypalSDK paypalSDK;
    private String apiToken;

    public PaypalAdapter(String paymentId, double amount, PaypalSDK paypalSDK, String apiToken) {
        super(paymentId, amount);
        this.paypalSDK = paypalSDK;
        this.apiToken = apiToken;
    }

    @Override
    public boolean processPayment() {
        int status = paypalSDK.executeCharge(amount, apiToken);
        return status == 200;
    }
}
