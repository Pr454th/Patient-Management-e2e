package com.pm.patient_service.kafka;

import com.pm.patient_service.model.Patient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

import java.util.UUID;

@Service
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    KafkaProducer(KafkaTemplate kafkaTemplate){
        this.kafkaTemplate=kafkaTemplate;
    }

    public void sendEvent(Patient patient){
        PatientEvent patientEvent=PatientEvent.newBuilder()
                .setPatientId(patient.getId().toString())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setEventType("PATIENT_CREATED").build();
        try{
            kafkaTemplate.send("patient", patientEvent.toByteArray());
        }
        catch(Exception e){
            log.error("Error while PatientCreated event: {} {}", patientEvent.toString(), e.toString());
        }
    }
}
