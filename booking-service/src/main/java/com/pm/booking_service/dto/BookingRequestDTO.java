package com.pm.booking_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDTO {
    @NotNull(message="PatientId is required")
    private String patientId;

    @NotNull(message = "SlotId is required")
    private String slotId;

    @NotNull(message = "Booking date is required")
    private LocalDate date;
}

