package com.hospital.config;

public class HospitalConfiguration {
    private static volatile HospitalConfiguration instance;

    private String hospitalName;
    private String hospitalCode;
    private String location;
    private String taxRegistrationNumber;

    private HospitalConfiguration() {
        // Prevent reflection-based instantiation
        if (instance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance.");
        }
        // Enterprise defaults
        this.hospitalName = "Apollo Multispecialty Hospital";
        this.hospitalCode = "APOLLO-BLR-001";
        this.location = "Bengaluru, Karnataka";
        this.taxRegistrationNumber = "GSTIN29AAAAA0000A1Z5";
    }

    public static HospitalConfiguration getInstance() {
        if (instance == null) {
            synchronized (HospitalConfiguration.class) {
                if (instance == null) {
                    instance = new HospitalConfiguration();
                }
            }
        }
        return instance;
    }

    public String getHospitalName() { return hospitalName; }
    public String getHospitalCode() { return hospitalCode; }
    public String getLocation() { return location; }
    public String getTaxRegistrationNumber() { return taxRegistrationNumber; }
}