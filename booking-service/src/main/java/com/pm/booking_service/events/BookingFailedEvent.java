package com.pm.booking_service.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingFailedEvent {
    private String status;
    private String bookingId;
    private String patientId;
    private String message;
}
