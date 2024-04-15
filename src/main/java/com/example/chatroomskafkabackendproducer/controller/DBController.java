package com.example.chatroomskafkabackendproducer.controller;

import com.example.chatroomskafkabackendproducer.dao.UserSocialDetailsRepository;
import com.example.chatroomskafkabackendproducer.pojo.UserSocialDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.cors.CorsConfiguration;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class DBController {

    private final UserSocialDetailsRepository userSocialDetailsRepository;


    @GetMapping("/test")
    public ResponseEntity<Object> test() {
        return ResponseEntity.ok().body(new Object() {
            public final String message = "Success";
        });
    }

    @PostMapping("/initUserSocialDetails")
    public ResponseEntity<?> socialDetailsInsertTest(@RequestBody Map<Object, Object> body) {
        String userId = (String) body.get("userId");

        UserSocialDetails userSocialDetails = new UserSocialDetails(userId);
        UserSocialDetails res = userSocialDetailsRepository.save(userSocialDetails);
        log.info("User LogIn, Created in DB: {}", res);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
