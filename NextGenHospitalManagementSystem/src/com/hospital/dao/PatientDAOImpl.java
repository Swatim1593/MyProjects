package com.hospital.dao;

import com.hospital.model.Patient;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PatientDAOImpl implements GenericDAO<Patient, String> {
    private final Map<String, Patient> patientMap = new ConcurrentHashMap<>();

    @Override
    public void save(Patient patient) {
        patientMap.put(patient.getId(), patient);
    }

    @Override
    public Optional<Patient> findById(String id) {
        return Optional.ofNullable(patientMap.get(id));
    }

    @Override
    public List<Patient> findAll() {
        return new ArrayList<>(patientMap.values());
    }

    @Override
    public void update(Patient patient) {
        patientMap.put(patient.getId(), patient);
    }

    @Override
    public void delete(String id) {
        patientMap.remove(id);
    }

    public Optional<Patient> findByPhone(String phone) {
        return patientMap.values().stream()
                .filter(p -> p.getPhone().equals(phone))
                .findFirst();
    }}