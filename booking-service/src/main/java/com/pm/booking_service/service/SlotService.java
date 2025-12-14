package com.pm.booking_service.service;

import com.pm.booking_service.model.Slot;

import java.util.UUID;

public interface SlotService {
    Slot getSlot(UUID slotId);
}
