package com.hotel.shmrs.exceptions;

public class HotelStorageException extends RuntimeException {
    public HotelStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}