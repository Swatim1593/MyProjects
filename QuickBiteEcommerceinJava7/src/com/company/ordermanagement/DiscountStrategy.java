package com.company.ordermanagement;

@FunctionalInterface
public interface DiscountStrategy {
    
    // Single Abstract Method (SAM)
    double calculateDiscount(double amount);

    // Default method for business logging
    default void printDiscountNotice(double originalAmount, double discount) {
        System.out.println("Original: $" + originalAmount + " | Discount: $" + discount + " | Net: $" + (originalAmount - discount));
    }
}