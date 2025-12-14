package com.pm.payment_service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pm.payment_service.events.PaymentCreationFailedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    @Autowired
    private KafkaTemplate<String, byte[]> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendPaymentSuccessEvent(){

    }

    public void sendPaymentFailedEvent(String bookingId, String message) throws JsonProcessingException {
        PaymentCreationFailedEvent failedEvent=PaymentCreationFailedEvent.builder()
                .bookingId(bookingId)
                .message(message)
                .build();
        byte[] data=objectMapper.writeValueAsBytes(failedEvent);
        kafkaTemplate.send("payments.failed", data);
    }
}
