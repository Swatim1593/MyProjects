package com.hospital.service;

import com.hospital.model.Patient;
import java.util.Comparator;
import java.util.PriorityQueue;

public class EmergencyPriorityQueue {
    // Min-heap ordering patients by priority level (Critical = 1)
    private final PriorityQueue<Patient> emergencyQueue = new PriorityQueue<>(
            Comparator.comparingInt(p -> p.getPriority().getLevel())
    );

    public synchronized void enqueueEmergencyPatient(Patient patient) {
        emergencyQueue.add(patient);
        System.out.println("  [EMERGENCY QUEUE] Enqueued Patient: " + patient.getName() + " | Priority: " + patient.getPriority());
    }

    public synchronized Patient dequeueNextPatient() {
        return emergencyQueue.poll();
    }

    public synchronized boolean isEmpty() {
        return emergencyQueue.isEmpty();
    }
}