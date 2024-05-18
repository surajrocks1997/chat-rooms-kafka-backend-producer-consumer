package com.example.chatroomskafkabackendproducer.controller;

import com.example.chatroomskafkabackendproducer.dao.UserRepository;
import com.example.chatroomskafkabackendproducer.dao.UserSocialDetailsRepository;
import com.example.chatroomskafkabackendproducer.pojo.CustomException;
import com.example.chatroomskafkabackendproducer.pojo.User;
import com.example.chatroomskafkabackendproducer.pojo.UserSocialDetails;
import com.example.chatroomskafkabackendproducer.service.MongoDbService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class DBController {

    private final UserSocialDetailsRepository userSocialDetailsRepository;
    private final UserRepository userRepository;
    private final MongoDbService mongoDbService;


    @GetMapping("/test")
    public ResponseEntity<Object> test() {
        return ResponseEntity.ok().body(new Object() {
            public final String message = "Success";
        });
    }

    @PostMapping("/initUserSocialDetails")
    public ResponseEntity<UserSocialDetails> initUserSocialDetails(@RequestBody Map<Object, Object> body) {
        String userId = String.valueOf(body.get("id"));

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

    @PostMapping("/users/userIds")
    public ResponseEntity<List<User>> getListOfUsersById(@RequestBody List<Long> ids) {
        List<User> allById = userRepository.findAllById(ids);
        return new ResponseEntity<>(allById, HttpStatus.OK);
    }

    @GetMapping("/sendFR/{receiverId}")
    public void sendFriendRequest(HttpServletRequest request, @PathVariable String receiverId) {

        String userId = getUserId(request);
        mongoDbService.sendFR(userId, receiverId);
    }

    @GetMapping("/acceptFR/{acceptedID}")
    public void acceptFriendRequest(HttpServletRequest request, @PathVariable String acceptedID) {
        String userId = getUserId(request);
        mongoDbService.acceptFR(userId, acceptedID);

    }

    private static String getUserId(HttpServletRequest request) {
        Map<String, Object> userMap = (Map<String, Object>) request.getAttribute("user");
        String userId = String.valueOf(userMap.get("id"));
        return userId;
    }
}
