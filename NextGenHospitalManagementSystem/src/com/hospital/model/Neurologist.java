package com.hospital.model;

public class Neurologist extends Doctor {
	
	
    
    /* Address docAddr = new Address("7th Block, Jayanagar", "Bengaluru", "Karnataka", "560011");
        Doctor cardioDoc = DoctorFactory.createDoctor("CARDIOLOGY", "DOC-101", "Dr. Devi Shetty", 62, Gender.MALE, "9845012345", docAddr, 1000.0);
        Doctor neuroDoc = DoctorFactory.createDoctor("NEUROLOGY", "DOC-102", "Dr. B. K. Misra", 58, Gender.MALE, "9845054321", docAddr, 1200.0);
*/
	 public Neurologist(String id, String name, int age, Gender gender, String phone, Address address, double fee) {
	        super(id, name, age, gender, phone, address, Department.NEUROLOGY, fee);
	    }

	    @Override
	    public double calculateConsultationFee() {
	        return baseConsultationFee + 700.00;
	    }
}
