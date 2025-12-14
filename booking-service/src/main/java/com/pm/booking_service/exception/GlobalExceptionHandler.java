package com.pm.booking_service.exception;

import com.pm.booking_service.dto.GeneralResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BookingSlotAlreadyExists.class)
    public ResponseEntity<GeneralResponse> handleBookingSlotAlreadyExistsException(BookingSlotAlreadyExists exception){
        GeneralResponse response=GeneralResponse.builder()
                .status("FAILED")
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
