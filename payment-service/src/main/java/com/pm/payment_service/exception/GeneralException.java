package com.pm.payment_service.exception;

public class GeneralException extends RuntimeException{
    String message;

    public GeneralException(String message){
        super(message);
        this.message=message;
    }
}
