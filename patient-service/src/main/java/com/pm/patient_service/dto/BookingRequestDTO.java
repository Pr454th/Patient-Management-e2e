package com.pm.patient_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class BookingRequestDTO {
    @NotNull(message="PatientId is required")
    private String patientId;

    @NotNull(message = "SlotId is required")
    private String slotId;

    @NotNull(message = "Booking date is required")
    private LocalDate date;
}
