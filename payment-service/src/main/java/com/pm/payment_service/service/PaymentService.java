package com.pm.payment_service.service;

import com.pm.payment_service.dto.PaymentRequestDTO;
import com.pm.payment_service.dto.PaymentResponseDTO;

import java.util.List;

public interface PaymentService {
    List<PaymentResponseDTO> getPayments();

    PaymentResponseDTO updatePayment(String paymentId, PaymentRequestDTO requestDTO);

    PaymentResponseDTO getPayment(String paymentId);
}
