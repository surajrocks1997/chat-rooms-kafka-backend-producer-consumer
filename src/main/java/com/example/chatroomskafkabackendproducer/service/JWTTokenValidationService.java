package com.example.chatroomskafkabackendproducer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class JWTTokenValidationService {

    private final RestTemplate restTemplate;

    public boolean isTokenValid(String authToken) {
        String url = "http://localhost:5000/api/auth/validateToken";
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-auth-token", authToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Object> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);

        return responseEntity.getStatusCode() == HttpStatus.OK;
    }
}
