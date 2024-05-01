package com.example.chatroomskafkabackendproducer.config;

import com.example.chatroomskafkabackendproducer.service.JWTTokenValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class RequestInterceptorConfig implements WebMvcConfigurer {

    private final JWTAuthInterceptor jwtAuthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(jwtAuthInterceptor)
                .addPathPatterns("/api/**")
                .addPathPatterns("/chat-rooms/**");
    }
}
