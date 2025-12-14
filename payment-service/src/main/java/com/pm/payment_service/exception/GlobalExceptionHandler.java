package com.pm.payment_service.exception;

import com.pm.payment_service.dto.GeneralResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PaymentNotExistsException.class)
    public ResponseEntity<GeneralResponse> handlePaymentNotExistsException(PaymentNotExistsException e){
        GeneralResponse response=GeneralResponse.builder()
                .status("FAILED")
                .result(e.getMessage())
                .build();

        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<GeneralResponse> handleGeneralException(GeneralException e){
        GeneralResponse response=GeneralResponse.builder()
                .status("FAILED")
                .result(e.getMessage())
                .build();

        return ResponseEntity.ok(response);
    }
}
