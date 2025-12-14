package com.pm.booking_service.controller;

import com.pm.booking_service.dto.BookingRequestDTO;
import com.pm.booking_service.dto.GeneralResponse;
import com.pm.booking_service.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping(path = "")
    public ResponseEntity<String> index(){
        return ResponseEntity.ok("Hello from booking service!");
    }

    @PostMapping(path = "")
    public ResponseEntity<GeneralResponse> book(@RequestBody BookingRequestDTO bookingRequestDTO){
        Boolean booked=bookingService.bookSlot(bookingRequestDTO);

        GeneralResponse response =
                GeneralResponse.builder()
                        .status(booked?"SUCCESS":"FAILED")
                        .message(booked?"Slot Booking Successful!, Track your Booking status in the status section":"Slot Booking Failed, try again")
                        .build();

        return ResponseEntity.ok(response);
    }
}
