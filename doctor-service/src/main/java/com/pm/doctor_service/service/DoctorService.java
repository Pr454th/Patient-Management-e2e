package com.pm.doctor_service.service;

import com.pm.doctor_service.dto.SlotRequestDTO;
import com.pm.doctor_service.dto.SlotResponseDTO;
import com.pm.doctor_service.model.Slot;
import com.pm.doctor_service.model.User;
import com.pm.doctor_service.repository.DoctorRepository;
import com.pm.doctor_service.repository.SlotRepository;
import com.pm.doctor_service.service.mapper.OperationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class DoctorService {
    @Autowired
    private DoctorRepository repository;

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private OperationMapper operationMapper;

    public boolean addSlot(SlotRequestDTO slotRequest) {
        try{
            slotRepository.save(operationMapper.toSlotModel(slotRequest));
            return true;
        }
        catch(Exception e){
            log.info("[ EXCEPTION ]: {}", e.getMessage());
            return false;
        }
    }

    public List<SlotResponseDTO> getSlots() {
        List<SlotResponseDTO> responseDTOS=
                slotRepository.findAll().stream().map(slot->operationMapper.toSlotDTO(slot))
                        .toList();
        return responseDTOS;
    }
}
