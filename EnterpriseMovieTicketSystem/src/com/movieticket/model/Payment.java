package com.movieticket.model;

import com.movieticket.enums.PaymentMethod;
import com.movieticket.enums.PaymentStatus;

public class Payment {
    private final String paymentId;
    private final String bookingId;
    private final double amount;
    private final PaymentMethod method;
    private PaymentStatus status;

    public Payment(String paymentId, String bookingId, double amount, PaymentMethod method) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.method = method;
        this.status = PaymentStatus.PENDING;
    }

    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }
}