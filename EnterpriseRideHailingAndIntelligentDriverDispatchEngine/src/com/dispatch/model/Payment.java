package com.dispatch.model;

import java.time.LocalDateTime;

import com.dispatch.enums.PaymentMode;
import com.dispatch.enums.PaymentStatus;

public class Payment {

	private final String paymentId;
	private final String rideId;
	private final double amount;
	private final PaymentMode paymentMode;
	private PaymentStatus status;
	private final LocalDateTime timestamp;

	public Payment(String paymentId, String rideId, double amount, PaymentMode paymentMode) {
		this.paymentId = paymentId;
		this.rideId = rideId;
		this.amount = amount;
		this.paymentMode = paymentMode;
		this.status = PaymentStatus.PENDING;
		this.timestamp = LocalDateTime.now();
	}

	public String getPaymentId() {
		return paymentId;
	}

	public String getRideId() {
		return rideId;
	}

	public double getAmount() {
		return amount;
	}

	public PaymentMode getPaymentMode() {
		return paymentMode;
	}

	public PaymentStatus getStatus() {
		return status;
	}

	public void setStatus(PaymentStatus status) {
		this.status = status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}
}