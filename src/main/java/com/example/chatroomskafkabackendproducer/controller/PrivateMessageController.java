package com.example.chatroomskafkabackendproducer.controller;

import com.example.chatroomskafkabackendproducer.pojo.PrivateChatMessage;
import com.example.chatroomskafkabackendproducer.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PrivateMessageController {

    private final KafkaProducerService producerService;

    @MessageMapping("/privateMessage/{receiver}")
    public void handlePrivateMessage(@DestinationVariable String receiver, @Payload PrivateChatMessage privateChatMessage) {

        switch (privateChatMessage.getMessageType()) {
            case SEND_FRIEND_REQUEST -> {
            }

            case PRIVATE_MESSAGE -> {
                privateChatMessage.setTimestamp(String.valueOf(System.currentTimeMillis()));
                log.info("Private Message: {}", privateChatMessage.toString());
                log.info("Receiver ID: {}", receiver);
            }

            default -> {
                log.warn("Unhandled Message Type: {}", privateChatMessage.getMessageType());
            }
        }

        producerService.privateProduce(privateChatMessage);
    }

}
