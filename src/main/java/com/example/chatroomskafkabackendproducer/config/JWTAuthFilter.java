package com.example.chatroomskafkabackendproducer.config;

import com.example.chatroomskafkabackendproducer.service.JWTTokenValidationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JWTAuthFilter extends OncePerRequestFilter {

    private final JWTTokenValidationService tokenValidationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (!response.isCommitted()) {
            final String authToken = request.getHeader("x-auth-token");

            log.info("URI: {}", request.getRequestURI());
            log.info("AUTH TOKEN: {}", authToken);

            if (authToken == null || authToken.isBlank() || authToken.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Unauthorized. Please Login to access the resource");
                return;
            }

            boolean isTokenValid = tokenValidationService.isTokenValid(authToken);

            if (!isTokenValid) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Unauthorized. Invalid Token. Please login again");
                return;
            }

        }

        filterChain.doFilter(request, response);
    }
}
