package com.hospital.model;

import com.hospital.factory.DoctorFactory;

public abstract class Doctor extends Person {
    protected Department department;
    protected double baseConsultationFee;
    
    
    /* Address docAddr = new Address("7th Block, Jayanagar", "Bengaluru", "Karnataka", "560011");
        Doctor cardioDoc = DoctorFactory.createDoctor("CARDIOLOGY", "DOC-101", "Dr. Devi Shetty", 62, Gender.MALE, "9845012345", docAddr, 1000.0);
        Doctor neuroDoc = DoctorFactory.createDoctor("NEUROLOGY", "DOC-102", "Dr. B. K. Misra", 58, Gender.MALE, "9845054321", docAddr, 1200.0);
*/
public Doctor(String id, String name, int age, Gender gender, String phone,
		Address address, Department department, double baseConsultationFee)
{
        super(id, name, age, phone,gender, address);
        this.department = department;
        this.baseConsultationFee = baseConsultationFee;
    }

    public Department getDepartment() { return department; }
    public abstract double calculateConsultationFee();

    @Override
    public String toString() {
        return String.format("Doctor[ID=%s, Name=%s, Dept=%s, Fee=₹%.2f]", id, name, department, calculateConsultationFee());
    }
}


