package com.pm.patient_service.contants;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Constants {
    public final static String BOOKING_SERVICE="http://booking-service";
    public final static String PAYMENT_SERVICE="http://payment-service";
}
