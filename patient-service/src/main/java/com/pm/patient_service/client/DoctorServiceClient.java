package com.pm.patient_service.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class DoctorServiceClient {

    @Autowired
    @Qualifier("doctorClient")
    private WebClient doctorClient;

    public String getDoctorEmail(String doctorId, String token){
        log.info("[ DOCTOR WEB CLIENT ]: {}", doctorClient.head(), String.format("/api/user/%s", doctorId));
        return this.doctorClient
                .get()
                .uri(String.format("/api/user/%s", doctorId))
                .header(HttpHeaders.AUTHORIZATION, token)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
