package com.quickbite.model;

import java.util.Objects;

public class Customer extends User {
    private String address;
    private Wallet wallet;

    public Customer() { super(); }

    public Customer(int id, String name, String email, long mobile, String address, double walletBalance) {
        super(id, name, email, mobile);
        this.address = address;
        this.wallet = new Wallet(walletBalance);
    }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Wallet getWallet() { return wallet; }

    @Override
    public void login() {
        System.out.println(">> Customer [" + getName() + "] logged in successfully.");
    }

    @Override
    public void logout() {
        System.out.println("<< Customer [" + getName() + "] logged out.");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return getId() == customer.getId() && Objects.equals(getEmail(), customer.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail());
    }
}
