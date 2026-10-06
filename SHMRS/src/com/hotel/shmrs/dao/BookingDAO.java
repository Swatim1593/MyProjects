package com.hotel.shmrs.dao;

import com.hotel.shmrs.model.entity.Booking;
import java.util.List;

public interface BookingDAO {
    void save(Booking booking);
    Booking findById(String bookingId);
    List<Booking> findAll();
}