package com.pm.patient_service.controller;

import com.pm.patient_service.dto.*;
import com.pm.patient_service.dto.validators.CreatePatientValidationGroup;
import com.pm.patient_service.service.OperationService;
import com.pm.patient_service.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@Tag(name="Patient", description = "API for managing patients")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @Autowired
    private OperationService operationService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    @Operation(summary = "Get Patients")
    public ResponseEntity<GeneralResponse> getPatients() {
        List<PatientResponseDTO> patientResponseDTOS = patientService.getPatients();
        GeneralResponse response=GeneralResponse.builder()
                .status("SUCCESS")
                .message("OK")
                .result(patientResponseDTOS)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/add")
    @Operation(summary = "Create new patient")
    public ResponseEntity<GeneralResponse> addPatient(@Validated({Default.class, CreatePatientValidationGroup.class}) @RequestBody PatientRequestDTO requestDTO) {
        PatientResponseDTO patientResponseDTO = patientService.createPatient(requestDTO);
        GeneralResponse response=GeneralResponse.builder()
                .status("SUCCESS")
                .message("OK")
                .result(patientResponseDTO)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    @Operation(summary = "Update patient")
    public ResponseEntity<GeneralResponse> updatePatient(@PathVariable("id") String uid, @Validated({Default.class}) @RequestBody PatientRequestDTO requestDTO){
        PatientResponseDTO responseDTO=patientService.updatePatient(UUID.fromString(uid), requestDTO);
        GeneralResponse response=GeneralResponse.builder()
                .status("SUCCESS")
                .message("Updated")
                .result(responseDTO)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete patient")
    public ResponseEntity<GeneralResponse> deletePatient(@PathVariable("id") String uid){
        String message= patientService.deletePatient(UUID.fromString(uid));
        GeneralResponse response=GeneralResponse.builder()
                .status("SUCCESS")
                .message(message)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(path = "/book")
    public ResponseEntity<GeneralResponse> bookSlot(@RequestBody BookingRequestDTO bookingRequest){
        GeneralResponse response=operationService.addBooking(bookingRequest);
        return ResponseEntity.ok(response);
    }

//    Eureka server test
    @GetMapping(path = "/test-eureka")
    public ResponseEntity<String> getDoctor(){
        String name=restTemplate.getForObject("http://doctor-service/doctors/test-name", String.class);

        return ResponseEntity.ok(name);
    }
}
