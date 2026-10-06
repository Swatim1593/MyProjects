package com.hotel.shmrs.model.entity;

public class Manager extends Employee {
    public Manager(String empId, String name, double salary) {
        super(empId, name, salary);
    }

    @Override
    public void performDuties() {
        System.out.println("[Staff: Manager " + name + "] Authorizing VIP allocations and inventory audits.");
    }
}