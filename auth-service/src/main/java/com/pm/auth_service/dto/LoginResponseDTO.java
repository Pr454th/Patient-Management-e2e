package com.pm.auth_service.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponseDTO {
    private String token;
}
