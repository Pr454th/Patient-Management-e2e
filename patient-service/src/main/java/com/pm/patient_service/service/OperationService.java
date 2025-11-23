package com.pm.patient_service.service;

import com.pm.patient_service.dto.BookingRequestDTO;
import com.pm.patient_service.dto.SlotRequestDTO;
import com.pm.patient_service.model.Slot;
import com.pm.patient_service.model.support.Status;
import com.pm.patient_service.repository.BookingRepository;
import com.pm.patient_service.repository.SlotRepository;
import com.pm.patient_service.service.mapper.OperationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class OperationService {

    @Autowired
    private BookingRepository repository;

    @Autowired
    private OperationMapper operationMapper;

    @Autowired
    private SlotRepository slotRepository;

    public boolean addBooking(BookingRequestDTO bookingRequest) {
        try{
            Optional<Slot> slot=slotRepository.findById(UUID.fromString(bookingRequest.getSlotId()));

            if(slot.isPresent()) {
                Slot currentSlot=slot.get();
                repository.save(operationMapper.toModel(bookingRequest, currentSlot, Status.BOOKED));
                return true;
            }

            return false;
        }
        catch(Exception e){
            log.info("[ EXCEPTION ]: {}", e.getMessage());
            return false;
        }
    }

    public boolean addSlot(SlotRequestDTO slotRequest) {

    }
}
