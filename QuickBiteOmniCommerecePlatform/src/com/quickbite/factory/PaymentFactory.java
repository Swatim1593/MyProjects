package com.quickbite.factory;

import com.quickbite.strategy.CreditCardPayment;
import com.quickbite.strategy.Payment;
import com.quickbite.strategy.PaypalAdapter;
import com.quickbite.strategy.PaypalSDK;
import com.quickbite.strategy.UPIPayment;
import com.quickbite.strategy.WalletPayment;

public class PaymentFactory {
	public static Payment createPayment(String type, String paymentId, double amount, String credential) {
		switch (type.toUpperCase()) {
		case "CARD":
			return new CreditCardPayment(paymentId, amount, credential);
		case "UPI":
			return new UPIPayment(paymentId, amount, credential);
		case "WALLET":
			return new WalletPayment(paymentId, amount, credential);
		case "PAYPAL":
			return new PaypalAdapter(paymentId, amount, new PaypalSDK(), credential);
			
		default:
			throw new IllegalArgumentException("Unsupported payment mode: " + type);
		}
	}
}

class Test{
	public static void main(String[] args) {
		
	Payment cardPayment=PaymentFactory.createPayment("CARD", "TXN1001", 49.99, "4111-XXXX-XXXXX-111");
	System.out.println("Created Payment:"+cardPayment.getClass().getSimpleName());
	
	Payment UPIPayment=PaymentFactory.createPayment("UPI", "TXN1002", 80.99, "User@UPI");
	System.out.println("Created Payment:"+UPIPayment.getClass().getSimpleName());
	}
	}