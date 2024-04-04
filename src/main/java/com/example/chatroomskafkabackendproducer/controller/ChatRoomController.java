package com.example.chatroomskafkabackendproducer.controller;

import com.example.chatroomskafkabackendproducer.pojo.ChatRoomMessage;
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
    public void getMessage(@Payload ChatRoomMessage message, @DestinationVariable String chatRoomName, SimpMessageHeaderAccessor headerAccessor) {
        switch (message.getMessageType()) {
            case CHAT_MESSAGE:
                message.setTimestamp(String.valueOf(System.currentTimeMillis()));
                producerService.produce(message);
                return;

            case USER_ONLINE:
            case USER_OFFLINE:
                headerAccessor.getSessionAttributes().put("username", message.getUsername());
                headerAccessor.getSessionAttributes().put("chatRoomName", chatRoomName);
                log.info(message.toString());
                producerService.produce(message);
                return;

            case USER_TYPING:
                // handle user typing:
                return;

            default:
                log.warn("Unidentifiable Message Type!!!");
        }
    }
}
