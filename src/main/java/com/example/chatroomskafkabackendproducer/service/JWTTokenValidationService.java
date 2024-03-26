package com.example.chatroomskafkabackendproducer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class JWTTokenValidationService {

    private final RestTemplate restTemplate;

    public boolean isTokenValid(String authToken) {
        String url = "http://localhost:5000/api/auth";
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-auth-token", authToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Object> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);

        return responseEntity.getStatusCode() == HttpStatus.OK;
    }
}
