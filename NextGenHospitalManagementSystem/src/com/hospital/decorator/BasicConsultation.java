package com.hospital.decorator;

public class BasicConsultation implements BillableService {
    private double baseFee;

    public BasicConsultation(double baseFee) {
        this.baseFee = baseFee;
    }

    @Override
    public double getCost() {
        return baseFee;
    }

    @Override
    public String getDescription() {
        return "Doctor Consultation Fee";
    }
}

