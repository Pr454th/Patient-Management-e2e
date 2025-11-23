package com.pm.auth_service.service.mapper;

import com.pm.auth_service.dto.UserRequestDTO;
import com.pm.auth_service.dto.UserResponseDTO;
import com.pm.auth_service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.UUID;

@Component
public class UserMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User toModel(UserRequestDTO requestDTO){
        return User.builder()
                .email(requestDTO.getEmail())
                .password(passwordEncoder.encode(requestDTO.getPassword()))
                .role("GUEST")
                .build();
    }

    public UserResponseDTO toDTO(User user){
        return UserResponseDTO.builder()
                .id(user.getId().toString())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
