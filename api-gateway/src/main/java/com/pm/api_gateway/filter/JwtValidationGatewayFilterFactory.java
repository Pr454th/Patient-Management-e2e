package com.pm.api_gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
public class JwtValidationGatewayFilterFactory extends AbstractGatewayFilterFactory {

    // for binding the auth service for interception
    private final WebClient webClient;

    private String authServiceUrl;

    public JwtValidationGatewayFilterFactory(WebClient.Builder webClientbuilder, @Value("${auth.service.url}") String authServiceUrl){
        this.authServiceUrl=authServiceUrl;
        this.webClient=webClientbuilder.build();
        log.info("[ WEBCLIENT ]: {} {}", webClient.toString(), this.authServiceUrl);
    }

    @Override
    public GatewayFilter apply(Object config){
//        exchange - passed by spring gateway, contains all the current properties
//        chain - chain of filters, other filters apart from this custom filter
        log.info("[ JWT FILTER CHAIN ]");
        return (exchange, chain) -> {
            String path=exchange.getRequest().getPath().toString();

            log.info("[ PATH ] : {} {}", path, authServiceUrl);

//            if (path.startsWith("/login") || path.startsWith("/register")) {
//                return chain.filter(exchange);
//            }

            String role=(path.contains("patients"))?"PATIENT":"DOCTOR";

            String token=exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            String uri=String.format(authServiceUrl+"/validate/%s", role);

            log.info("[ URI ]: {}", uri);

            if(token==null || !token.startsWith("Bearer ")){
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            return this.webClient
                    .get()
                    .uri(uri)
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .retrieve()
                    .toBodilessEntity()
                    .then(chain.filter(exchange));
//                    .onErrorResume(e -> {
//                        // If WebClient throws 401 or any error, return 401 to gateway client
//                        log.info("[ ERROR ]: {}", e.getMessage());
//                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//                        return exchange.getResponse().setComplete();
//                    });
        };
    }
}
