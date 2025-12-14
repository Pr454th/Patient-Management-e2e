package com.pm.booking_service.repository;

import com.pm.booking_service.model.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SlotRepository extends JpaRepository<Slot, UUID> {
}
