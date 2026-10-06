package com.hotel.shmrs.model.entity;

public abstract class Employee {
    protected final String empId;
    protected final String name;
    protected final double baseSalary;

    public Employee(String empId, String name, double baseSalary) {
        this.empId = empId;
        this.name = name;
        this.baseSalary = baseSalary;
    }

    public abstract void performDuties();
}