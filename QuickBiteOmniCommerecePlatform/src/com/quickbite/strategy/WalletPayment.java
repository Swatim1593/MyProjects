package com.quickbite.strategy;

public class WalletPayment extends Payment {
    private String walletId;

    public WalletPayment(String paymentId, double amount, String walletId) {
        super(paymentId, amount);
        this.walletId = walletId;
    }

    @Override
    public boolean processPayment() {
        System.out.println("  [WALLET PAYMENT] Deducting ₹" + amount + " from Wallet ID: " + walletId);
        return true;
    }
}
