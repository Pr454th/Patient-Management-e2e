package com.pm.doctor_service.controller;

import com.pm.doctor_service.dto.GeneralResponse;
import com.pm.doctor_service.dto.SlotRequestDTO;
import com.pm.doctor_service.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping(path = "/slot")
    public ResponseEntity<GeneralResponse> addSlot(@RequestBody SlotRequestDTO slotRequest){
        boolean added=doctorService.addSlot(slotRequest);
        GeneralResponse response=GeneralResponse.builder()
                .status(added?"SUCCESS":"FAILED")
                .message(added?"Slot Added successfully!":"Slot unsuccessful!")
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/slots")
    public ResponseEntity<GeneralResponse> getSlots(){
        GeneralResponse response=GeneralResponse.builder()
                .status("SUCCESS")
                .message("All available slots")
                .result(doctorService.getSlots())
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/test-name")
    public ResponseEntity<String> getTestName(){
        return ResponseEntity.ok("Doctor 1");
    }
}
