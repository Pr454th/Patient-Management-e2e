package com.pm.payment_service.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pm.payment_service.dto.PaymentRequestDTO;
import com.pm.payment_service.dto.PaymentResponseDTO;
import com.pm.payment_service.events.BookingEvent;
import com.pm.payment_service.exception.DummyException;
import com.pm.payment_service.exception.GeneralException;
import com.pm.payment_service.exception.PaymentNotExistsException;
import com.pm.payment_service.kafka.KafkaProducer;
import com.pm.payment_service.model.Payment;
import com.pm.payment_service.model.support.PaymentMode;
import com.pm.payment_service.model.support.Status;
import com.pm.payment_service.repository.PaymentRepository;
import com.pm.payment_service.service.PaymentService;
import com.pm.payment_service.service.mapper.GlobalMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GlobalMapper mapper;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Override
    public List<PaymentResponseDTO> getPayments() {
        List<Payment> payments=repository.findAll();

        List<PaymentResponseDTO> responseDTOS=payments.stream()
                .map(payment->mapper.toDTO(payment)).toList();

        return responseDTOS;
    }

    @Override
    public PaymentResponseDTO getPayment(String paymentId) {
        Payment payment=repository.findById(UUID.fromString(paymentId)).orElse(null);
        if(payment==null){
            throw new GeneralException("No payment found with id :"+paymentId);
        }
        return mapper.toDTO(payment);
    }

    @KafkaListener(topics = "bookings", groupId = "Payment-Service")
    public void handlePayment(byte[] data) throws JsonProcessingException {
        BookingEvent bookingEvent=null;
        Payment payment=null;
        try {
            bookingEvent = objectMapper.readValue(data, BookingEvent.class);

            payment=Payment.builder()
                    .paymentId(UUID.fromString(bookingEvent.getBookingId()))
                    .modeOfPayment(PaymentMode.CARD)
                    .openingTime(LocalDateTime.now())
                    .status(Status.PENDING)
                    .build();
            repository.save(payment);

            log.info("[ BOOKING EVENT ]: {}", bookingEvent.getBookingId());
//            throw new DummyException();
        }
        catch(Exception e){
            log.info("[ Exception ]: {}", e.getCause());
            if(payment!=null){
                repository.delete(payment);
            }
            kafkaProducer.sendPaymentFailedEvent(bookingEvent.getBookingId(), "Encountered an issue while payment creation");
        }
    }

    @Override
    public PaymentResponseDTO updatePayment(String paymentId, PaymentRequestDTO requestDTO) {
        PaymentResponseDTO responseDTO=null;
        try{
            Payment payment=repository.findById(UUID.fromString(paymentId)).orElse(null);
            if(payment==null){
                throw new PaymentNotExistsException("Requested payment does not exist!");
            }
            payment.setModeOfPayment(requestDTO.getModeOfPayment());
            payment.setBilledAmount(requestDTO.getBilledAmount());
            payment.setBalanceDue(requestDTO.getBilledAmount());
            payment.setBalanceDue(payment.getBalanceDue()-requestDTO.getAmountPaid());
            payment.setStatus(payment.getBalanceDue()==0?Status.PAID:Status.PENDING);

            return mapper.toDTO(payment);
        }
        catch(Exception e){
            log.info("[ Exception ]: {}", e.getMessage());
            throw new GeneralException("Error while updating payment record");
        }
    }
}
