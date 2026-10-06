package com.hospital.exception;

public class InvalidPatientDataException extends RuntimeException {
    public InvalidPatientDataException(String message) { super(message); }
}