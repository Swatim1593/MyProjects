package com.speedfeast.core;

public class Customer {
	private int customerId;
	private String customerName;
	private boolean PrimeMember;
	private double walletBalance;
	
	public Customer() {
	}
	public Customer(int CustomerId, String CustomerName, double walletBalance,boolean primeMember) {
		this.customerId=customerId;
		this.customerName=customerName;
		this.PrimeMember=PrimeMember;
		this.walletBalance=walletBalance;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId=customerId;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName=customerName;
	}
	public boolean isPrimeMember() {
		return PrimeMember;
	}
	public void getPrimeMember() {
		this.PrimeMember=PrimeMember;
	}
	public double getwalletBalance() {
		return walletBalance;
	}
	public void setWalletBalance(double walletBalance) {
		this.walletBalance=walletBalance;
	}
	
	
	}




