package com.company.ordermanagement;

public class PremiumDiscount implements DiscountStrategy {

    @Override
    public double calculateDiscount(double amount) {
        return amount * 0.15; // 15% discount for premium customers
    }
}