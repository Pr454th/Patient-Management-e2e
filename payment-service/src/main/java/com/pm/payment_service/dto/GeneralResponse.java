package com.pm.payment_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeneralResponse<T> {
    @NotBlank
    private String status;

    private T result;
}
