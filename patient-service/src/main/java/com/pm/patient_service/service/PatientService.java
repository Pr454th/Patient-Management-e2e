package com.pm.patient_service.service;

import com.pm.patient_service.dto.PatientRequestDTO;
import com.pm.patient_service.dto.PatientResponseDTO;
import com.pm.patient_service.dto.PatientUpdateRequestDTO;
import com.pm.patient_service.exception.EmailAlreadyExistsException;
import com.pm.patient_service.exception.PatientNotFoundException;
import com.pm.patient_service.grpc.BillingServiceGrpcClient;
import com.pm.patient_service.kafka.KafkaProducer;
import com.pm.patient_service.model.Patient;
import com.pm.patient_service.repository.PatientRepository;
import com.pm.patient_service.service.mapper.PatientMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private BillingServiceGrpcClient serviceGrpcClient;

    public List<PatientResponseDTO> getPatients(){
        List<Patient> patients=patientRepository.findAll();

        List<PatientResponseDTO> patientResponseDTOS=patients.stream()
                .map((patient)->PatientMapper.toDTO(patient)).toList();

        return patientResponseDTOS;
    }

    public PatientResponseDTO createPatient(PatientRequestDTO requestDTO){
        if(patientRepository.existsByEmail(requestDTO.getEmail())){
            throw new EmailAlreadyExistsException("Email already exists", "EMAIL_EXISTS");
        }
        Patient response=patientRepository.save(PatientMapper.toModel(requestDTO));

        serviceGrpcClient.createBillingAccount(response.getId().toString(), response.getName(), response.getEmail());

        kafkaProducer.sendEvent(response);

        return PatientMapper.toDTO(response);
    }

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO requestDTO){
        Patient patient=(Patient)patientRepository.findById(id).orElseThrow(()->new PatientNotFoundException("patient not found", "PATIENT_NOT_FOUND"));;

        if(!patient.getEmail().equalsIgnoreCase(requestDTO.getEmail()) && patientRepository.existsByEmail(requestDTO.getEmail())){
            throw new EmailAlreadyExistsException("Email already exists", "EMAIL_EXISTS");
        }

        patient.setName(requestDTO.getName());
        patient.setEmail(requestDTO.getEmail());
        patient.setAddress(requestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(requestDTO.getDateOfBirth()));

        return PatientMapper.toDTO(patientRepository.save(patient));
    }

    public String deletePatient(UUID id){
        patientRepository.deleteById(id);
        return "Deleted!";
    }
}
