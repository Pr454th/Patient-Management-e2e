package com.pm.patient_service.exception;

public class PatientNotFoundException extends RuntimeException {
    String errorCode;
    public PatientNotFoundException(String message, String errorCode){
        super(message);
        this.errorCode=errorCode;
    }
}
