package com.example.chatroomskafkabackendproducer.service;

import com.example.chatroomskafkabackendproducer.pojo.ChatRoomMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final StreamBridge streamBridge;
    private final UserPresenceHandler userPresenceHandler;

    public void produce(ChatRoomMessage message) {
        switch (message.getMessageType()) {
            case USER_ONLINE:
                Set<String> userPresence = userPresenceHandler.addPresenceToMap(message.getChatRoomName(), message.getUsername());
                message.setAdditionalData(userPresence);
                break;

            case USER_OFFLINE:
                Set<String> newUserPresence = userPresenceHandler.removePresenceFromMap(message.getChatRoomName(), message.getUsername());
                message.setAdditionalData(newUserPresence);
                break;

            case USER_TYPING:
                log.info("Message Type: {}", message.getMessageType());
                break;

            default:
                log.warn("Unregistered message type: {}", message.getMessageType());

        }


        Message<ChatRoomMessage> build = MessageBuilder.withPayload(message).build();
        streamBridge.send("producer-out-0", build);
    }
}
