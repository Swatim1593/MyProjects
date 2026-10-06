package com.hospital.facade;

import com.hospital.dao.AppointmentDAOImpl;
import com.hospital.decorator.BasicConsultation;
import com.hospital.decorator.BillableService;
import com.hospital.decorator.LabTestDecorator;
import com.hospital.decorator.PharmacyDecorator;
import com.hospital.model.*;
import com.hospital.observer.NotificationObserver;
import com.hospital.service.PatientService;
import com.hospital.strategy.PaymentStrategy;
import com.hospital.strategy.UpiPayment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class HospitalFacade {
    private final PatientService patientService;
    private final AppointmentDAOImpl appointmentDAO;
    private final List<NotificationObserver> observers = new ArrayList<>();
    private final AtomicLong appointmentSeq = new AtomicLong(5001);

    public HospitalFacade(PatientService patientService, AppointmentDAOImpl appointmentDAO) {
        this.patientService = patientService;
        this.appointmentDAO = appointmentDAO;
    }

    public void registerObserver(NotificationObserver observer) {
        observers.add(observer);
    }

    private void notifyAllObservers(String message) {
        for (NotificationObserver observer : observers) {
            observer.update(message);
        }
    }
  //        Appointment appt1 = hospitalFacade.bookAppointment(patient1.getId(), cardioDoc, LocalDateTime.now().plusDays(1));

    public Appointment bookAppointment(String patientId, Doctor doctor, LocalDateTime appointmentTime) {
        Patient patient = patientService.getPatient(patientId);

        String apptId = "APT-" + appointmentSeq.getAndIncrement();
        Appointment appointment = new Appointment(apptId, patient.getId(), doctor.getId(), appointmentTime);
        appointmentDAO.save(appointment);

        notifyAllObservers("Appointment #" + apptId + " booked for " + patient.getName() + " with Dr. " + doctor.getName() + " at " + appointmentTime);
        return appointment;
    }
    
    /*hospitalFacade.processFullConsultationAndBilling(patient1.getId(),cardioDoc,"ECG&Lipid Profile",1500.00,450.00,new UpiPayment("jyothi@okaxis")); 
        System.out.println();
*/

    public double processFullConsultationAndBilling(String patientId, Doctor doctor, String testName, double testCost, double pharmacyCost, PaymentStrategy paymentStrategy) {
        Patient patient = patientService.getPatient(patientId);

        // Decorator pattern dynamically aggregates costs
        BillableService bill = new BasicConsultation(doctor.calculateConsultationFee());
        if (testName != null && testCost > 0) {
            bill = new LabTestDecorator(bill, testName, testCost);
        }
        if (pharmacyCost > 0) {
            bill = new PharmacyDecorator(bill, pharmacyCost);
        }

        double totalAmount = bill.getCost();
        System.out.println("  [BILL GENERATED] Breakdown: " + bill.getDescription() + " | Gross Total: ₹" + totalAmount);

        // Execute Strategy
        boolean success = paymentStrategy.processPayment(totalAmount);
        if (success) {
            notifyAllObservers("Invoice paid successfully for " + patient.getName() + " | Total: ₹" + totalAmount);
        }

        return totalAmount;
    }
}