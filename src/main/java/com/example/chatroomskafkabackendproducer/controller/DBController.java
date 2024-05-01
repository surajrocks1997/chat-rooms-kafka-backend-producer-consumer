package com.example.chatroomskafkabackendproducer.controller;

import com.example.chatroomskafkabackendproducer.dao.UserRepository;
import com.example.chatroomskafkabackendproducer.dao.UserSocialDetailsRepository;
import com.example.chatroomskafkabackendproducer.pojo.CustomException;
import com.example.chatroomskafkabackendproducer.pojo.User;
import com.example.chatroomskafkabackendproducer.pojo.UserSocialDetails;
import com.example.chatroomskafkabackendproducer.service.DBService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class DBController {

    private final UserSocialDetailsRepository userSocialDetailsRepository;
    private final UserRepository userRepository;
    private final DBService dbService;


    @GetMapping("/test")
    public ResponseEntity<Object> test() {
        return ResponseEntity.ok().body(new Object() {
            public final String message = "Success";
        });
    }

    @PostMapping("/initUserSocialDetails")
    public ResponseEntity<UserSocialDetails> initUserSocialDetails(@RequestBody Map<Object, Object> body) {
        String userId = String.valueOf(body.get("userId"));

        UserSocialDetails userSocialDetails = new UserSocialDetails(userId);
        UserSocialDetails res = userSocialDetailsRepository.save(userSocialDetails);
        log.info("User LogIn, Created in DB: {}", res);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/getUserSocialDetails/{userId}")
    public ResponseEntity<UserSocialDetails> getUserSocialDetails(@PathVariable String userId) {
        Optional<UserSocialDetails> user = userSocialDetailsRepository.findByUserId(userId);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else throw new CustomException("Invalid User!!", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable long userId) {
        Optional<User> getUser = userRepository.findById(userId);
        if (getUser.isPresent()) {
            User user = getUser.get();
            return new ResponseEntity<>(User
                    .builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .build(), HttpStatus.OK
            );
        } else throw new CustomException("User Not Found!!", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/sendFR/{receiverId}")
    public void sendFriendRequest(HttpServletRequest request, @PathVariable String receiverId) {

        Map<String, Object> userMap = (Map<String, Object>) request.getAttribute("user");
        String userId = String.valueOf(userMap.get("id"));
        dbService.sendFR(userId, receiverId);
    }
}
