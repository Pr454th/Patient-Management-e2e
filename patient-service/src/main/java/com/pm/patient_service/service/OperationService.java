package com.pm.patient_service.service;

import com.pm.patient_service.client.DoctorServiceClient;
import com.pm.patient_service.dto.BookingRequestDTO;
import com.pm.patient_service.dto.NotificationDTO;
import com.pm.patient_service.dto.SlotRequestDTO;
import com.pm.patient_service.kafka.KafkaProducer;
import com.pm.patient_service.model.Slot;
import com.pm.patient_service.model.support.Status;
import com.pm.patient_service.repository.BookingRepository;
import com.pm.patient_service.repository.SlotRepository;
import com.pm.patient_service.repository.projection.BookedSlotsProjection;
import com.pm.patient_service.service.mapper.OperationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private DoctorServiceClient doctorServiceClient;

    public boolean addBooking(BookingRequestDTO bookingRequest, String token) {
        try{
            BookedSlotsProjection bookedSlots=repository.bookedSlots(bookingRequest.getDate());

            if(bookedSlots==null) {
                log.info("[ SLOT AVAILABLE ]");
                Slot currentSlot=slotRepository.findById(UUID.fromString(bookingRequest.getSlotId())).get();
                log.info("SLOT: {}", currentSlot.toString());
                repository.save(operationMapper.toBookingModel(bookingRequest, currentSlot, Status.BOOKED));

                kafkaProducer.sendBookingNotification(NotificationDTO.builder()
                                .email(doctorServiceClient.getDoctorEmail(currentSlot.getDoctorId().toString(), token).toString())
                                .subject("SLOT BOOKED")
                                .content("Booking Date : "+bookingRequest.getDate().toString()+"\nSlot : "+currentSlot.getSlotFrame())
                        .build());

                return true;
            }
            return false;
        }
        catch(Exception e){
            log.info("[ EXCEPTION ]: {}", e.getMessage());
            return false;
        }
    }

//    This function should belong to doctor service
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
}
