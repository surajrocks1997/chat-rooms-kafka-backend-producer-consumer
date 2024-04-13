package com.example.chatroomskafkabackendproducer.controller;

import com.example.chatroomskafkabackendproducer.pojo.PrivateChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PrivateMessageController {

    private final SimpMessageSendingOperations messageTemplate;

    @MessageMapping("/privateMessage/{receiver}")
    public void handlePrivateMessage(@DestinationVariable String receiver, @Payload PrivateChatMessage privateChatMessage) {
        privateChatMessage.setTimestamp(String.valueOf(System.currentTimeMillis()));
        log.info("Private Message: {}", privateChatMessage.toString());
        log.info("Receiver ID: {}", receiver);

//        /user/{receiver}/queue/messages
        messageTemplate.convertAndSendToUser(receiver, "/queue/messages", privateChatMessage);

    }

}
