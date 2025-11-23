package com.pm.patient_service.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class PatientResponseDTO {
    private UUID id;
    private String name;
    private String email;
    private String address;
    private String dateOfBirth;
}
