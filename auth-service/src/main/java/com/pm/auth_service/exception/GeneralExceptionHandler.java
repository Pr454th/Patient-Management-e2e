package com.pm.auth_service.exception;

import com.pm.auth_service.dto.GeneralResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralExceptionHandler extends Exception{

    @ExceptionHandler(UserCreationException.class)
    public ResponseEntity<GeneralResponse> handleUserCreationException(UserCreationException exception){
        GeneralResponse response=GeneralResponse.builder()
                .status("FAILED")
                .result(exception.getMessage())
                .build();
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(UserExistsWithEmail.class)
    public ResponseEntity<GeneralResponse> handleUserExistsWithEmailException(UserExistsWithEmail exception){
        GeneralResponse response=GeneralResponse.builder()
                .status("FAILED")
                .result(exception.getMessage())
                .build();
        return ResponseEntity.ok(response);
    }

}
