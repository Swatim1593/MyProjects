package com.hospital.model;

public class GeneralPhysician extends Doctor {
    public GeneralPhysician(String id, String name, int age, Gender gender, String phone, Address address, double fee) {
        super(id, name, age, gender, phone, address, Department.GENERAL_MEDICINE, fee);
    }

    @Override
    public double calculateConsultationFee() {
        return baseConsultationFee;
    }
}