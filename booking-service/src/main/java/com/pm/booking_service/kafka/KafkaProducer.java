package com.pm.booking_service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pm.booking_service.events.BookingEvent;
import com.pm.booking_service.events.BookingFailedEvent;
import com.pm.booking_service.model.Booking;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducer {
    private KafkaTemplate<String, byte[]> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    KafkaProducer(KafkaTemplate kafkaTemplate){
        this.kafkaTemplate=kafkaTemplate;
    }

    public void sendBookingEvent(Booking booking){
        try {
            BookingEvent bookingEvent = BookingEvent.builder()
                    .bookingId(booking.getBookingId().toString())
                    .build();
            byte[] data = objectMapper.writeValueAsBytes(bookingEvent);
            kafkaTemplate.send("bookings", data);
        }
        catch(Exception e){
            log.info("Encountered issue while booking the event: {}", e.getMessage());
        }
    }

    public void sendBookingFailedEvent(String status, String bookingId, String patientId, String message) {
        try {
            BookingFailedEvent bookingEvent = BookingFailedEvent.builder()
                    .bookingId(bookingId)
                    .patientId(patientId)
                    .status(status)
                    .message(message)
                    .build();
            byte[] data = objectMapper.writeValueAsBytes(bookingEvent);
            kafkaTemplate.send("bookings.failed", data);
        }
        catch(Exception e){
            log.info("Encountered issue while sending booking.failed event: {}", e.getMessage());
        }
    }
}
