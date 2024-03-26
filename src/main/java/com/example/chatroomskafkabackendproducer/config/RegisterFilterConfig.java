package com.example.chatroomskafkabackendproducer.config;

import com.example.chatroomskafkabackendproducer.service.JWTTokenValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RegisterFilterConfig {

    private final JWTTokenValidationService jwtTokenValidationService;

    @Bean
    public FilterRegistrationBean<JWTAuthFilter> jwtAuthFilterFilterRegistrationBean() {
        FilterRegistrationBean<JWTAuthFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new JWTAuthFilter(jwtTokenValidationService));
        registrationBean.addUrlPatterns("/api/*");

        return registrationBean;
    }
}
