package com.pm.auth_service.service;

import com.pm.auth_service.dto.LoginRequestDTO;
import com.pm.auth_service.dto.LoginResponseDTO;
import com.pm.auth_service.model.User;
import com.pm.auth_service.util.JWTUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtil jwtUtil;

    public Optional<String> authenticate(LoginRequestDTO requestDTO){
        Optional<String> token=userService
                .findByEmail(requestDTO.getEmail())
                .filter(u->passwordEncoder.matches(requestDTO.getPassword(), u.getPassword()))
                .map(u->jwtUtil.generateToken(u.getEmail(), u.getRole()));
        return token;
    }

    public boolean validateToken(String token, String role) {
        try{
            jwtUtil.validateToken(token, role);
            return true;
        }
        catch(JwtException exception){
            return false;
        }
    }
}
