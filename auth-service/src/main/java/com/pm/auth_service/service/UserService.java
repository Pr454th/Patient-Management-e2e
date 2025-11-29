package com.pm.auth_service.service;

import com.pm.auth_service.dto.UserRequestDTO;
import com.pm.auth_service.dto.UserResponseDTO;
import com.pm.auth_service.exception.UserCreationException;
import com.pm.auth_service.exception.UserExistsWithEmail;
import com.pm.auth_service.model.User;
import com.pm.auth_service.repository.UserRepository;
import com.pm.auth_service.service.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public UserResponseDTO register(UserRequestDTO userRequestDTO) {
        User newUser=null;

        boolean isUserExists=userRepository.findByEmail(userRequestDTO.getEmail()).isPresent();

        if(isUserExists){
            throw new UserExistsWithEmail("User exists with the same email");
        }

        newUser = userRepository.save(userMapper.toModel(userRequestDTO));

        return userMapper.toDTO(newUser);
    }

    public String getEmail(String doctorId) {
        Optional<User> user=userRepository.findById(UUID.fromString(doctorId));

        if(user.isPresent()){
            return user.get().getEmail();
        }

        return "";
    }
}
