package com.hospital.decorator;

public abstract class ConsultationDecorator implements BillableService {
    protected BillableService decoratedService;

    public ConsultationDecorator(BillableService decoratedService) {
        this.decoratedService = decoratedService;
    }
}

