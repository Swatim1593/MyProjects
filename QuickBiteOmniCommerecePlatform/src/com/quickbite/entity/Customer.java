// File: com/quickbite/entity/Customer.java
package com.quickbite.entity;

public class Customer extends User {
    private String city;
    private boolean premium;

    public Customer(int userId, String name, String email, Address address, String city, boolean premium) {
        super(userId, name, email, address);
        this.city = city;
        this.premium = premium;
    }

    public String getCity() {
    	return city;
    	}
    public boolean isPremium() {
    	return premium;
    	}
    public void setPremium(boolean premium) {
    	this.premium = premium;
    	}

    @Override
    public String toString() {
        return String.format("Customer #%d: %s (%s) [Premium: %b]", userId, name, city, premium);
    }
}