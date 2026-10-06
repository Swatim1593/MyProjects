package com.hospital;

import java.time.LocalDateTime;

import com.hospital.config.HospitalConfiguration;
import com.hospital.dao.AppointmentDAOImpl;
import com.hospital.dao.PatientDAOImpl;
import com.hospital.dto.PatientDTO;
import com.hospital.facade.HospitalFacade;
import com.hospital.factory.DoctorFactory;
import com.hospital.model.Address;
import com.hospital.model.Appointment;
import com.hospital.model.Doctor;
import com.hospital.model.Gender;
import com.hospital.model.Patient;
import com.hospital.model.PatientPriority;
import com.hospital.observer.EmailNotificationService;
import com.hospital.observer.SmsNotificationService;
import com.hospital.service.EmergencyPriorityQueue;
import com.hospital.service.PatientService;
import com.hospital.strategy.UpiPayment;

public class HospitalManagementApplication {

	public static void main(String[] args) {
		System.out.println("======================================");
		System.out.println("INITIALIZING ENTERPRISE HOSPITAL MANAGEMENT SYSTEM");
		System.out.println("====================================================\n");
		
		//1.SINGLETON VERIFICATION
		HospitalConfiguration config =HospitalConfiguration.getInstance();
		System.out.println("Loaded Config "+config.getHospitalName()+"("+config.getHospitalCode()+")");
        System.out.println("Location: "+config.getLocation()+"GSTIN:"+config.getTaxRegistrationNumber()+"\n");
        
        // 2. LAYER & DEPENDENCY SETUP
        PatientDAOImpl patientDAO = new PatientDAOImpl();
        AppointmentDAOImpl appointmentDAO = new AppointmentDAOImpl();
        PatientService patientService = new PatientService(patientDAO);
        HospitalFacade hospitalFacade = new HospitalFacade(patientService, appointmentDAO);
        

        // Attach Notification observers
        hospitalFacade.registerObserver(new SmsNotificationService());
        hospitalFacade.registerObserver(new EmailNotificationService());
        
        //3.FACTORY PATTERN:REGISTER DOCTORS
        // 3. FACTORY PATTERN: REGISTER DOCTORS
        
        
        Address docAddr = new Address("7th Block, Jayanagar", "Bengaluru", "Karnataka", "560011");
        Doctor cardioDoc = DoctorFactory.createDoctor("CARDIOLOGY", "DOC-101", "Dr. Devi Shetty", 62, Gender.MALE, "9845012345", docAddr, 1000.0);
        Doctor neuroDoc = DoctorFactory.createDoctor("NEUROLOGY", "DOC-102", "Dr. B. K. Misra", 58, Gender.MALE, "9845054321", docAddr, 1200.0);

        System.out.println("--- REGISTERED DOCTORS ---");
        System.out.println(cardioDoc);
        System.out.println(neuroDoc);
        System.out.println();
        
        
        
        
     // 4. PATIENT REGISTRATION (DTO & BUILDER PATTERN)
        
        
        System.out.println("--- 1. PATIENT REGISTRATION ---");
        PatientDTO dto1 = new PatientDTO("Jyothi Prasad", 35, Gender.MALE, "9876543210", "100 Residency Rd", "Bengaluru", "KA", "560025", "O+", "POL-998877");
        PatientDTO dto2 = new PatientDTO("Ramesh Rao", 68, Gender.MALE, "9123456789", "45 Indiranagar", "Bengaluru", "KA", "560038", "A+", "POL-112233");

        Patient patient1 = patientService.registerPatient(dto1);
        Patient patient2 = patientService.registerPatient(dto2);
        System.out.println("Registered: " + patient1);
        System.out.println("Registered: " + patient2);
        System.out.println();
        
        // 5. APPOINTMENT BOOKING (FACADE PATTERN & OBSERVER PATTERN)
        
        
        System.out.println("--- 2. BOOKING APPOINTMENT ---");
        Appointment appt1 = hospitalFacade.bookAppointment(patient1.getId(), cardioDoc, LocalDateTime.now().plusDays(1));
        System.out.println("Confirmed: " + appt1);
        System.out.println();
        
        
        
        //6.CONSULTATION DECORATOR BILLING &STRATEGY PAYMENT
        System.out.println("------3 CONSULTATION ,DIAGNOSTICS &PAYMENT-----");
        hospitalFacade.processFullConsultationAndBilling(patient1.getId(),cardioDoc,"ECG&Lipid Profile",1500.00,450.00,new UpiPayment("jyothi@okaxis")); 
        System.out.println();

        
        
        // 7. STREAM API: FILTER SENIOR CITIZENS
        System.out.println("--- 4. JAVA 8 STREAMS: SENIOR CITIZEN RECORDS ---");
        patientService.getSeniorCitizens().forEach(p -> System.out.println("  -> " + p));
        System.out.println();

        
       
        // 8. DATA STRUCTURE: EMERGENCY PRIORITY QUEUE (MAX/MIN HEAP)
        System.out.println("--- 5. EMERGENCY ROOM HEAP DISPATCHING ---");
        EmergencyPriorityQueue erQueue = new EmergencyPriorityQueue();

        Patient criticalPatient = new Patient.Builder()
                .setId("PAT-999")
                .setName("Accident Victim")
                .setAge(28)
                .setGender(Gender.MALE)
                .setPhone("9000000000")
                .setPriority(PatientPriority.CRITICAL)
                .build();

        Patient lowPriorityPatient = new Patient.Builder()
                .setId("PAT-998")
                .setName("Mild Fever Patient")
                .setAge(22)
                .setGender(Gender.FEMAL)
                .setPhone("9111111111")
                .setPriority(PatientPriority.LOW)
                .build();

        erQueue.enqueueEmergencyPatient(lowPriorityPatient);
        erQueue.enqueueEmergencyPatient(criticalPatient);

        System.out.println("Attending Emergency Patients by Priority:");
        while (!erQueue.isEmpty()) {
            Patient p = erQueue.dequeueNextPatient();
            System.out.println("  [DOCTOR ATTENDING NOW] " + p.getName() + " | Priority: " + p.getPriority());
        }

        System.out.println("\n=========================================================");
        System.out.println("   HOSPITAL MANAGEMENT SYSTEM RUNTIME EXECUTION COMPLETED");
        System.out.println("=========================================================");
    }


	}


