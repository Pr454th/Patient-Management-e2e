package com.pm.booking_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SlotRequestDTO {
    @NotNull(message = "Doctor Id is required")
    private String doctorId;

    @NotBlank(message = "Slot Frame cannot be empty")
    private String slotFrame;
}

