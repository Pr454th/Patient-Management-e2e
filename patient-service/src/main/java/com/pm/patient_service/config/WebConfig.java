package com.pm.patient_service.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Slf4j
public class WebConfig {

    @Bean
    @Qualifier("doctorClient")
    public WebClient getDoctorClient(WebClient.Builder webclientBuilder, @Value("${client.doctor.uri}") String url){
        log.info("[ WC ]: {}", url);
        return webclientBuilder.baseUrl(url).build();
    }

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
