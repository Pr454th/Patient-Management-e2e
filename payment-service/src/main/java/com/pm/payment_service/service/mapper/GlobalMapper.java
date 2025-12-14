package com.pm.payment_service.service.mapper;

import com.pm.payment_service.dto.PaymentResponseDTO;
import com.pm.payment_service.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class GlobalMapper {
    public PaymentResponseDTO toDTO(Payment payment){
        return PaymentResponseDTO.builder()
                .openingTime(payment.getOpeningTime())
                .status(payment.getStatus())
                .balanceDue(payment.getBalanceDue())
                .billedAmount(payment.getBilledAmount())
                .closingTime(payment.getClosingTime())
                .modeOfPayment(payment.getModeOfPayment())
                .paymentId(payment.getPaymentId().toString())
                .build();
    }
}
