package com.pm.patient_service.service.mapper;

import com.pm.patient_service.dto.BookingRequestDTO;
import com.pm.patient_service.model.Booking;
import com.pm.patient_service.model.Slot;
import com.pm.patient_service.model.support.Status;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OperationMapper {
    public Booking toModel(BookingRequestDTO request, Slot slot, Status status){
        return Booking.builder()
                .doctorId(slot.getDoctorId())
                .slotId(slot.getSlotId())
                .patientId(UUID.fromString(request.getPatientId()))
                .bookingDate(request.getDate())
                .status(status)
                .build();
    }
}
