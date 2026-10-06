package com.hotel.shmrs.model.entity;

import com.hotel.shmrs.model.enums.RoomType;

public class PremiumRoom extends Room {
	public PremiumRoom(int roomNumber) {
		super(roomNumber,RoomType.PREMIUM);
	}
	@Override
	public double calculatePrice(int nights) {
		return roomType.getBaseRate()*nights*(1+roomType.getTaxRate());
	}

}
