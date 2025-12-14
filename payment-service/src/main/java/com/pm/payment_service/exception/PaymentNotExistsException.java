package com.pm.payment_service.exception;

public class PaymentNotExistsException extends RuntimeException{
    String message;

    public PaymentNotExistsException(String message){
        super(message);
        this.message=message;
    }
}
