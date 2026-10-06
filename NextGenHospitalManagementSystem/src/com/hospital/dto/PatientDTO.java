package com.hospital.dto;

import com.hospital.model.Gender;

public class PatientDTO {
    private String name;
    private int age;
    private Gender gender;
    private String phone;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String bloodGroup;
    private String insurancePolicyNo;

    public PatientDTO(String name, int age, Gender gender, String phone, String street, String city, String state, String zipCode, String bloodGroup, String insurancePolicyNo) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.bloodGroup = bloodGroup;
        this.insurancePolicyNo = insurancePolicyNo;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public Gender getGender() { return gender; }
    public String getPhone() { return phone; }
    public String getStreet() { return street; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getZipCode() { return zipCode; }
    public String getBloodGroup() { return bloodGroup; }
    public String getInsurancePolicyNo() { return insurancePolicyNo; }
}