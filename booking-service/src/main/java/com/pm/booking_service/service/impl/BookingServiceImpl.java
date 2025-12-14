package com.pm.booking_service.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pm.booking_service.dto.BookingRequestDTO;
import com.pm.booking_service.events.PaymentCreationFailedEvent;
import com.pm.booking_service.exception.BookingSlotAlreadyExists;
import com.pm.booking_service.kafka.KafkaProducer;
import com.pm.booking_service.model.Booking;
import com.pm.booking_service.model.Slot;
import com.pm.booking_service.model.support.Status;
import com.pm.booking_service.repository.BookingRepository;
import com.pm.booking_service.repository.projection.BookedSlotsProjection;
import com.pm.booking_service.service.BookingService;
import com.pm.booking_service.service.SlotService;
import com.pm.booking_service.service.mapper.BookingSlotMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository repository;

    @Autowired
    private SlotService slotService;

    @Autowired
    private BookingSlotMapper mapper;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Boolean bookSlot(BookingRequestDTO bookingRequest) {
        BookedSlotsProjection bookedSlots=repository.bookedSlots(bookingRequest.getDate());

        if(bookedSlots==null) {
            log.info("[ SLOT AVAILABLE ]");
            Slot currentSlot=slotService.getSlot(UUID.fromString(bookingRequest.getSlotId()));
            log.info("SLOT: {}", currentSlot.toString());
            Booking newBook=repository.save(mapper.toBookingModel(bookingRequest, currentSlot, Status.BOOKED));
            kafkaProducer.sendBookingEvent(newBook);
            return true;
        }
        else {
            throw new BookingSlotAlreadyExists("Booking slot already exists!");
        }
    }

    @KafkaListener(topics = "payments.failed", groupId = "Booking-Service")
    public void handlePaymentStatus(byte[] data) throws JsonProcessingException {
        PaymentCreationFailedEvent paymentEvent=null;
        try {
            paymentEvent = objectMapper.readValue(data, PaymentCreationFailedEvent.class);

            Booking booking=repository.findById(UUID.fromString(paymentEvent.getBookingId())).orElse(null);

            booking.setStatus(Status.FAILED);
            booking.setMessage(paymentEvent.getMessage());
            booking.setLastUpdated(LocalDateTime.now());

            repository.save(booking);

            kafkaProducer.sendBookingFailedEvent("FAILED", paymentEvent.getBookingId(), booking.getPatientId().toString(), paymentEvent.getMessage());

            log.info("[ PAYMENT EVENT ]: {}", paymentEvent.getBookingId());
        }
        catch(Exception e){
            log.info("[ Exception ]: {}", e.getMessage());
        }
    }
}
