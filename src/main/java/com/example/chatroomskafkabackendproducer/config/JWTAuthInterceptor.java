package com.example.chatroomskafkabackendproducer.config;

import com.example.chatroomskafkabackendproducer.service.JWTTokenValidationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
@Slf4j
public class JWTAuthInterceptor implements HandlerInterceptor {

    private final JWTTokenValidationService jwtTokenValidationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String authToken = request.getHeader("x-auth-token");

        log.debug("URI: {}", request.getRequestURI());
        log.info("AUTH TOKEN: {}", authToken);


        return jwtTokenValidationService.isTokenValid(authToken);
    }
}
