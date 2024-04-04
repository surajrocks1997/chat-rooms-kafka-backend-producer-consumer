package com.example.chatroomskafkabackendproducer.controller;

import com.example.chatroomskafkabackendproducer.pojo.Message;
import com.example.chatroomskafkabackendproducer.pojo.UserPresence;
import com.example.chatroomskafkabackendproducer.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatRoomController {

    private final KafkaProducerService producerService;

    @MessageMapping("/chatRoom/{chatRoomName}")
    public void getMessage(@Payload Message message, @DestinationVariable String chatRoomName) {
        message.setTimestamp(String.valueOf(System.currentTimeMillis()));
        producerService.produce(message);
    }

    @MessageMapping("/chatRoom/{chatRoomName}/userPresence")
    public void updateUserPresence(@Payload UserPresence userPresence, @DestinationVariable String chatRoomName, SimpMessageHeaderAccessor headerAccessor) {
        // logic to publish all subscribers with UserPresence Indicator
        headerAccessor.getSessionAttributes().put("email", userPresence.getEmail());
        headerAccessor.getSessionAttributes().put("chatRoomName", chatRoomName);

        log.info(userPresence.toString());
    }
}
