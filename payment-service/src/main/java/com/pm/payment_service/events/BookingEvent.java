package com.pm.payment_service.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingEvent {
    private String bookingId;
}
