package com.hospital.model;

import java.util.ArrayList;
import java.util.List;


public class MedicalRecord implements Cloneable {
    private String recordId;
    private String patientId;
    private List<String> diagnoses;
    private List<String> prescriptions;
    private List<String> allergies;

    public MedicalRecord(String recordId, String patientId) {
        this.recordId = recordId;
        this.patientId = patientId;
        this.diagnoses = new ArrayList<>();
        this.prescriptions = new ArrayList<>();
        this.allergies = new ArrayList<>();
    }

    public void addDiagnosis(String diagnosis) {
    	diagnoses.add(diagnosis); 
    	}
    public void addPrescription(String prescription) { 
    	prescriptions.add(prescription);
    	}
    public void addAllergy(String allergy) { 
    	allergies.add(allergy); 
    	}

    public String getRecordId() { 
    	return recordId; 
    	}
    public String getPatientId() { 
    	return patientId; 
    	}
    public List<String> getDiagnoses() { return diagnoses; }
    public List<String> getPrescriptions() { return prescriptions; }

    // Prototype Pattern implementation for historical record snapshots
    @Override
    public MedicalRecord clone() {
        try {
            MedicalRecord cloned = (MedicalRecord) super.clone();
            cloned.diagnoses = new ArrayList<>(this.diagnoses);
            cloned.prescriptions = new ArrayList<>(this.prescriptions);
            cloned.allergies = new ArrayList<>(this.allergies);
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        return String.format("MedicalRecord[ID=%s, PatientID=%s, Diagnoses=%s, Prescriptions=%s]",
                recordId, patientId, diagnoses, prescriptions);
    }
}