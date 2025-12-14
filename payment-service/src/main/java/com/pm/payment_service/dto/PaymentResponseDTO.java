package com.pm.payment_service.dto;

import com.pm.payment_service.model.support.PaymentMode;
import com.pm.payment_service.model.support.Status;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class PaymentResponseDTO {
    private String paymentId;

    private LocalDateTime openingTime;

    private LocalDateTime closingTime;

    private PaymentMode modeOfPayment;

    private Long billedAmount;

    private Long balanceDue;

    private Status status;
}
