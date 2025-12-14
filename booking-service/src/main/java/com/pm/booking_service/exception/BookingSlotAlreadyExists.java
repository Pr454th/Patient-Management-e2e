package com.pm.booking_service.exception;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class BookingSlotAlreadyExists extends RuntimeException{
    private String message;

    public BookingSlotAlreadyExists(String message){
        super(message);
        this.message=message;
    }
}
