package com.pm.auth_service.exception;

public class UserCreationException extends RuntimeException{
    private String message;

    public UserCreationException(String message){
        super(message);
        this.message=message;
    }
}
