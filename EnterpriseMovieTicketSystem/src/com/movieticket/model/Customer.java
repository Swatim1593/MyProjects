package com.movieticket.model;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.movieticket.enums.UserRole;

public class Customer extends User {
	private final List<Booking>bookingHistory;
	
	public Customer(String id, String name,String email, String phone, String password) {
		super (id,name,email, phone, password, UserRole.CUSTOMER);
		this.bookingHistory=new CopyOnWriteArrayList<>();
	}
	public List<Booking>getBookings(){
		return bookingHistory;
	}
	public void addBooking(Booking booking) {
		this.bookingHistory.add(booking);
	}

}
