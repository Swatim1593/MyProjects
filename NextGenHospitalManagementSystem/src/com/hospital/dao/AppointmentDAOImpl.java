package com.hospital.dao;



import com.hospital.model.Appointment;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class AppointmentDAOImpl implements GenericDAO<Appointment, String> {
    private final Map<String, Appointment> appointmentMap = new ConcurrentHashMap<>();

    @Override
    public void save(Appointment appointment) {
        appointmentMap.put(appointment.getAppointmentId(), appointment);
    }

    @Override
    public Optional<Appointment> findById(String id) {
        return Optional.ofNullable(appointmentMap.get(id));
    }

    @Override
    public List<Appointment> findAll() {
        return new ArrayList<>(appointmentMap.values());
    }

    @Override
    public void update(Appointment appointment) {
        appointmentMap.put(appointment.getAppointmentId(), appointment);
    }

    @Override
    public void delete(String id) {
        appointmentMap.remove(id);
    }
}