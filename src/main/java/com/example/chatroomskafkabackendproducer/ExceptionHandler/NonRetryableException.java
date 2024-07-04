package com.example.chatroomskafkabackendproducer.ExceptionHandler;

public class NonRetryableException extends RuntimeException {
    public NonRetryableException(Throwable cause) {
        super(cause);
    }

    public NonRetryableException(String message) {
        super(message);
    }
}
