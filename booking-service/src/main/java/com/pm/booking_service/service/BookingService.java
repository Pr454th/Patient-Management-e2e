package com.pm.booking_service.service;

import com.pm.booking_service.dto.BookingRequestDTO;

public interface BookingService {
    Boolean bookSlot(BookingRequestDTO bookingRequestDTO);
}
