package com.pm.auth_service.controller;

import com.pm.auth_service.dto.LoginRequestDTO;
import com.pm.auth_service.dto.LoginResponseDTO;
import com.pm.auth_service.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/")
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Generate token on user login")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO requestDTO
            ){
        Optional<String> tokenOptional=authService.authenticate(requestDTO);

        if(tokenOptional.isEmpty()){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String token=tokenOptional.get();

        return new ResponseEntity<>(LoginResponseDTO.builder().token(token).build(), HttpStatus.OK);
    }

    @Operation(summary = "Validate Token")
    @GetMapping("/validate/{role}")
    public ResponseEntity<Void> validateToken(@PathVariable("role") String role, @RequestHeader("Authorization") String authHeader){

        // Authorization: Bearer <token>

        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        log.info("[ VALIDATE ]");

        return authService.validateToken(authHeader.substring(7), role)
                ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
