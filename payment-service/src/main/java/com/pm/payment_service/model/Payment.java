package com.pm.payment_service.model;

import com.pm.payment_service.model.support.PaymentMode;
import com.pm.payment_service.model.support.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Payments")
public class Payment {
    @Id
    @NotNull
    private UUID paymentId;

    @NotNull
    private LocalDateTime openingTime;

    private LocalDateTime closingTime;

    @Enumerated(EnumType.STRING)
    private PaymentMode modeOfPayment;

    private Long billedAmount;

    private Long balanceDue;

    @Enumerated(EnumType.STRING)
    private Status status;
}
