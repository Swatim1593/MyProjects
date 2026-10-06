package com.hospital.decorator;

public class PharmacyDecorator extends ConsultationDecorator {
    private double medicineCost;

    public PharmacyDecorator(BillableService decoratedService, double medicineCost) {
        super(decoratedService);
        this.medicineCost = medicineCost;
    }

    @Override
    public double getCost() {
        return decoratedService.getCost() + medicineCost;
    }

    @Override
    public String getDescription() {
        return decoratedService.getDescription() + " + Pharmacy Prescriptions";
    }
}