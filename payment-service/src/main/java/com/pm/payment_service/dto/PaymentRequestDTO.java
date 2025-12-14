package com.pm.payment_service.dto;

import com.pm.payment_service.model.support.PaymentMode;
import com.pm.payment_service.model.support.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentRequestDTO {

    private PaymentMode modeOfPayment;

    private Long billedAmount;

    private Long amountPaid;

    private Status status;
}
