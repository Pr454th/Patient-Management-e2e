package com.pm.patient_service.exception;

import com.pm.patient_service.dto.GeneralResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GeneralResponse> handleValidationException(MethodArgumentNotValidException exception){
        Map<String, String> errors=new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach((error)->{
            errors.put(error.getField(), error.getDefaultMessage());
        });

        GeneralResponse response=GeneralResponse.builder()
                .status("FAILED")
                .message("VALIDATION")
                .result(errors)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<GeneralResponse> handleEmailExistsException(EmailAlreadyExistsException exception){
        GeneralResponse response=GeneralResponse.builder()
                .status("FAILED")
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<GeneralResponse> handlePatientNotFoundException(PatientNotFoundException exception){
        GeneralResponse response=GeneralResponse.builder()
                .status("FAILED")
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
