package com.pm.doctor_service.service.mapper;

import com.pm.doctor_service.dto.SlotRequestDTO;
import com.pm.doctor_service.dto.SlotResponseDTO;
import com.pm.doctor_service.model.Slot;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OperationMapper {
    public Slot toSlotModel(SlotRequestDTO request){
        return Slot.builder()
                .doctorId(UUID.fromString(request.getDoctorId()))
                .slotFrame(request.getSlotFrame())
                .build();
    }

    public SlotResponseDTO toSlotDTO(Slot slot){
        return SlotResponseDTO.builder()
                .doctorId(slot.getDoctorId().toString())
                .slotFrame(slot.getSlotFrame())
                .slotId(slot.getSlotId().toString())
                .build();
    }
}
