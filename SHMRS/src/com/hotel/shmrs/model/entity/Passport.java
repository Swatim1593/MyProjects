package com.hotel.shmrs.model.entity;

import java.io.Serializable;

public class Passport implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String passportNumber;
    private final String countryCode;

    public Passport(String passportNumber, String countryCode) {
        this.passportNumber = passportNumber;
        this.countryCode = countryCode;
    }

    public String getPassportNumber() { return passportNumber; }
    public String getCountryCode() { return countryCode; }

    @Override
    public String toString() {
        return "Passport[" + passportNumber + " (" + countryCode + ")]";
    }
}