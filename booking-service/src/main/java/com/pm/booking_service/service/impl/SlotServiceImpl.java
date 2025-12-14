package com.pm.booking_service.service.impl;

import com.pm.booking_service.model.Slot;
import com.pm.booking_service.repository.SlotRepository;
import com.pm.booking_service.service.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SlotServiceImpl implements SlotService {

    @Autowired
    private SlotRepository repository;

    @Override
    public Slot getSlot(UUID slotId) {
        Optional<Slot> slot=repository.findById(slotId);
        return slot.orElse(null);
    }
}
