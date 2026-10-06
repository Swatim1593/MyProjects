package com.company.ordermanagement;

public class RegularDiscount implements DiscountStrategy {

    @Override
    public double calculateDiscount(double amount) {
        return amount * 0.05; // 5% discount for regular customers
    }
}