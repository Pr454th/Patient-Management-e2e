package com.pm.patient_service.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    private String errorCode;
    public EmailAlreadyExistsException(String message, String errorCode){
        super(message);
        this.errorCode=errorCode;
    }
}
