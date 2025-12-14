package com.pm.booking_service.service.mapper;

import com.pm.booking_service.dto.BookingRequestDTO;
import com.pm.booking_service.dto.SlotRequestDTO;
import com.pm.booking_service.model.Booking;
import com.pm.booking_service.model.Slot;
import com.pm.booking_service.model.support.Status;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class BookingSlotMapper {
    public Booking toBookingModel(BookingRequestDTO request, Slot slot, Status status){
        return Booking.builder()
                .doctorId(slot.getDoctorId())
                .slotId(slot.getSlotId())
                .patientId(UUID.fromString(request.getPatientId()))
                .bookingDate(request.getDate())
                .slotFrame(slot.getSlotFrame())
                .message("")
                .lastUpdated(LocalDateTime.now())
                .status(status)
                .build();
    }

    public Slot toSlotModel(SlotRequestDTO request){
        return Slot.builder()
                .doctorId(UUID.fromString(request.getDoctorId()))
                .slotFrame(request.getSlotFrame())
                .build();
    }
}
