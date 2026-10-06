package com.hospital.factory;

import com.hospital.model.*;

public class DoctorFactory {
	
    
    /* Address docAddr = new Address("7th Block, Jayanagar", "Bengaluru", "Karnataka", "560011");
        Doctor cardioDoc = DoctorFactory.createDoctor("CARDIOLOGY", "DOC-101", "Dr. Devi Shetty", 62, Gender.MALE, "9845012345", docAddr, 1000.0);
        Doctor neuroDoc = DoctorFactory.createDoctor("NEUROLOGY", "DOC-102", "Dr. B. K. Misra", 58, Gender.MALE, "9845054321", docAddr, 1200.0);
*/
	
    public static Doctor createDoctor(String type, String id, String name, int age, Gender gender, String phone, Address address, double baseFee) {
        switch (type.toUpperCase()) {
            case "CARDIOLOGY":
                return new Cardiologist(id, name, age, gender, phone, address, baseFee);
            case "NEUROLOGY":
                return new Neurologist(id, name, age, gender, phone, address, baseFee);
            case "GENERAL":
            default:
                return new GeneralPhysician(id, name, age, gender, phone, address, baseFee);
        }
    }
}