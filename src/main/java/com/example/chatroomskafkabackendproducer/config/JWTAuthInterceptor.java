package com.example.chatroomskafkabackendproducer.config;

import com.example.chatroomskafkabackendproducer.pojo.CustomException;
import com.example.chatroomskafkabackendproducer.service.JWTTokenValidationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
@Slf4j
public class JWTAuthInterceptor implements HandlerInterceptor {

    private final JWTTokenValidationService jwtTokenValidationService;
    private final RestTemplate restTemplate;

    @Value("${app.auth-server.url}")
    private String authServerUrl;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("From Auth Interceptor********");
        log.info(request.getMethod());
        if (request.getMethod().equals("OPTIONS")) {
            // preflight request
            return true;
        }

        final String authToken = request.getHeader("x-auth-token");

        log.debug("URI: {}", request.getRequestURI());
        log.info("AUTH TOKEN: {}", authToken);


        if (!jwtTokenValidationService.isTokenValid(authToken)) {
            return false;
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-auth-token", authToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            log.warn("Auth Server URL: {}", authServerUrl);

            ResponseEntity<Object> res = null;
            try {
                res = restTemplate.exchange(authServerUrl + "/auth", HttpMethod.GET, entity, Object.class);
            } catch (RestClientException e) {
                throw new CustomException(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            request.setAttribute("user", res.getBody());
        }

        return true;
    }
}
