package com.pm.auth_service.controller;

import com.pm.auth_service.dto.GeneralResponse;
import com.pm.auth_service.dto.UserRequestDTO;
import com.pm.auth_service.dto.UserResponseDTO;
import com.pm.auth_service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.groups.Default;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "User registration")
    @PostMapping(path = "register")
    public ResponseEntity<GeneralResponse> registerUser(@Validated({Default.class}) @RequestBody UserRequestDTO userRequestDTO){
        UserResponseDTO responseDTO=userService.register(userRequestDTO);

        if(responseDTO==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        GeneralResponse response=GeneralResponse.builder()
                .status("SUCCESS")
                .result(responseDTO)
                .build();

        return ResponseEntity.ok(response);
    }
}
