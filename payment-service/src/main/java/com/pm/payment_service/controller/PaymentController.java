package com.pm.payment_service.controller;

import com.pm.payment_service.dto.GeneralResponse;
import com.pm.payment_service.dto.PaymentRequestDTO;
import com.pm.payment_service.dto.PaymentResponseDTO;
import com.pm.payment_service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping(path="")
    public ResponseEntity<GeneralResponse> getPayments(){
        List<PaymentResponseDTO> responseDTOs=paymentService.getPayments();

        GeneralResponse response=GeneralResponse.builder()
                .status("SUCCESS")
                .result(responseDTOs)
                .build();

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<GeneralResponse> getPaymentInfo(@PathVariable("id") String paymentId){
        PaymentResponseDTO responseDTOs=paymentService.getPayment(paymentId);

        GeneralResponse response=GeneralResponse.builder()
                .status("SUCCESS")
                .result(responseDTOs)
                .build();

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @PutMapping(path="/update/{id}")
    public ResponseEntity<GeneralResponse> updatePayment(@PathVariable("id") String paymentId, @RequestBody PaymentRequestDTO requestDTO){
        PaymentResponseDTO responseDTO=paymentService.updatePayment(paymentId, requestDTO);

        GeneralResponse response=GeneralResponse.builder()
                .status("SUCCESS")
                .result(responseDTO)
                .build();

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}
