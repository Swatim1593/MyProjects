package com.hotel.shmrs.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Customer implements Serializable, Comparable<Customer> {
    private static final long serialVersionUID = 1L;

    private final String customerId;
    private final String name;
    private final String email;
    private final boolean isVIP;
    private final Passport passport; // One-to-One
    private final List<HotelService> optedServices = new ArrayList<>(); // Many-to-Many
    
    // transient keyword ensures sensitive PII is NEVER saved during serialization
    private final transient String maskedSecretPin;

    public Customer(String customerId, String name, String email, boolean isVIP, Passport passport, String maskedSecretPin) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.isVIP = isVIP;
        this.passport = passport;
        this.maskedSecretPin = maskedSecretPin;
    }

    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public boolean isVIP() { return isVIP; }
    public Passport getPassport() { return passport; }
    public List<HotelService> getOptedServices() { return optedServices; }
    public String getMaskedSecretPin() { return maskedSecretPin; }

    public void addService(HotelService service) {
        this.optedServices.add(service);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        return Objects.equals(customerId, customer.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId);
    }

    @Override
    public int compareTo(Customer o) {
        return this.customerId.compareTo(o.customerId);
    }

    @Override
    public String toString() {
        return "Customer[ID=" + customerId + ", Name=" + name + ", VIP=" + isVIP + 
               ", " + passport + ", SecretPIN=" + (maskedSecretPin == null ? "NULL/PROTECTED" : maskedSecretPin) + "]";
    }
}