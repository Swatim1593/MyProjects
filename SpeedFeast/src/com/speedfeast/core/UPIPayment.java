package com.speedfeast.core;

public class UPIPayment implements Payment {
	private String upiId;
	public UPIPayment(String upiId) {
		this.upiId=upiId;
	}
	public boolean pay(double amount,Customer customer) {
		if(upiId!=null && upiId.contains("@")){
			System.out.println("[GATEWAY] handshaking external upi rout for id:"+upiId);
			System.out.println("[GATEWAY]remitted"+amount+"successfully");
			return true;
		}
		return false;
		}
	
	}



