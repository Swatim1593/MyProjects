package com.hotel.shmrs.model.entity;

public class Receptionist extends Employee {
    public Receptionist(String empId, String name, double salary) {
        super(empId, name, salary);
    }

    @Override
    public void performDuties() {
        System.out.println("[Staff: Receptionist " + name + "] Managing guest check-in terminal.");
    }
}