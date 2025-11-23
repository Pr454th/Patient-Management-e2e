package com.pm.patient_service.controller;

import com.pm.patient_service.dto.BookingRequestDTO;
import com.pm.patient_service.dto.GeneralResponse;
import com.pm.patient_service.dto.SlotRequestDTO;
import com.pm.patient_service.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/operations")
public class OperationController {

    @Autowired
    private OperationService operationService;

    @PostMapping(path = "/slot")
    public ResponseEntity<GeneralResponse> addSlot(@RequestBody SlotRequestDTO slotRequest){
        boolean booked=operationService.addSlot(slotRequest);
        GeneralResponse response=GeneralResponse.builder()
                .status(booked?"SUCCESS":"FAILED")
                .message(booked?"Booking Confirmed":"Booking failed!")
                .build();
        return ResponseEntity.ok(response);
    }

}
