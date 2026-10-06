package com.speedfeast.core;

public class WalletPayment implements Payment {
    @Override
    public boolean pay(double amount, Customer customer) {
        if (customer.getwalletBalance() >= amount) {
            customer.setWalletBalance(customer.getwalletBalance() -amount);
            return true;
        }
        return false;
    }
}