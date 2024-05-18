package com.example.chatroomskafkabackendproducer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
//@EnableScheduling
public class ChatRoomsKafkaBackendProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatRoomsKafkaBackendProducerApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper mapper() {
        return new ObjectMapper();
    }

}
