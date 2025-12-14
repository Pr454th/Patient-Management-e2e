package com.pm.booking_service.repository.projection;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BookedSlotsProjection {
    private UUID bookedSlotId;
}
