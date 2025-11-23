package com.pm.auth_service.exception;

public class UserExistsWithEmail extends RuntimeException{
    private String message;

    public UserExistsWithEmail(String message){
        super(message);
        this.message=message;
    }
}
