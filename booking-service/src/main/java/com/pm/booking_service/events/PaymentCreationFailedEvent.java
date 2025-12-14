package com.pm.booking_service.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentCreationFailedEvent {
    private String bookingId;
    private String message;
}
