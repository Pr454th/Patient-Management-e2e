package com.pm.patient_service.service.mapper;

import com.pm.patient_service.dto.PatientRequestDTO;
import com.pm.patient_service.dto.PatientResponseDTO;
import com.pm.patient_service.dto.PatientUpdateRequestDTO;
import com.pm.patient_service.model.Patient;

import java.time.LocalDate;
import java.util.UUID;

public class PatientMapper {
    public static PatientResponseDTO toDTO(Patient patient){
        PatientResponseDTO patientResponseDTO=PatientResponseDTO.builder()
                .id(patient.getId())
                .name(patient.getName())
                .email(patient.getEmail())
                .address(patient.getAddress())
                .dateOfBirth(patient.getDateOfBirth().toString())
                .build();
        return patientResponseDTO;
    }

    public static Patient toModel(PatientRequestDTO patientDTO){
        Patient patient=Patient.builder()
                .name(patientDTO.getName())
                .email(patientDTO.getEmail())
                .address(patientDTO.getAddress())
                .dateOfBirth(LocalDate.parse(patientDTO.getDateOfBirth()))
                .registeredDate(LocalDate.parse(patientDTO.getRegisteredDate()))
                .build();
        return patient;
    }
}
