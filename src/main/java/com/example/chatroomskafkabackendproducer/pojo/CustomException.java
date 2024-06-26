package com.example.chatroomskafkabackendproducer.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@Builder
public class CustomException extends RuntimeException {
    private String errorMessage;
    private HttpStatusCode statusCode;

}
