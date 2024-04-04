package com.example.chatroomskafkabackendproducer.ExceptionHandler;

import com.example.chatroomskafkabackendproducer.pojo.CustomException;
import com.example.chatroomskafkabackendproducer.pojo.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorMessage(), ex.getStatusCode());
        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorResponse> handleHttpClientErrorException(HttpClientErrorException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getResponseBodyAsString(), e.getStatusCode());
        return new ResponseEntity<>(errorResponse, e.getStatusCode());
    }
}
