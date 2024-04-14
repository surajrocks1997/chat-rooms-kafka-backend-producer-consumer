package com.example.chatroomskafkabackendproducer.controller;

import com.example.chatroomskafkabackendproducer.dao.MongoTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TestController {

    private final MongoTestRepository mongoTestRepository;

    @GetMapping("/test")
    public ResponseEntity<Object> test() {
        return ResponseEntity.ok().body(new Object() {
            public final String message = "Success";
        });
    }

    @GetMapping("/testMongoDb")
    public ResponseEntity<Object> testMongodb() {
        return ResponseEntity.ok().body(new Object() {
            public final String message = "Success";
            public final Object data = mongoTestRepository.findAll();
        });
    }
}
