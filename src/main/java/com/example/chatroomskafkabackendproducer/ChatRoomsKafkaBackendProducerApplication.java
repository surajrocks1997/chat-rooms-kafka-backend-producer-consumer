package com.example.chatroomskafkabackendproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ChatRoomsKafkaBackendProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatRoomsKafkaBackendProducerApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
