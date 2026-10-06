package com.company.ordermanagement;

public class Customer {

    private final int customerId;
    private final String name;
    private final String city;
    private final boolean premium;

    public Customer(int customerId, String name, String city, boolean premium) {
        this.customerId = customerId;
        this.name = name;
        this.city = city;
        this.premium = premium;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public boolean isPremium() {
        return premium;
    }

    @Override
    public String toString() {
        return customerId + " " + name + " " + city + " " + (premium ? "Premium" : "Regular");
    }
}