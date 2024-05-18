package com.example.chatroomskafkabackendproducer.service;

import com.example.chatroomskafkabackendproducer.pojo.ChatRoomMessage;
import com.example.chatroomskafkabackendproducer.pojo.PrivateChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final UserPresenceHandler userPresenceHandler;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void produce(ChatRoomMessage message) {
        switch (message.getMessageType()) {
            case CHAT_MESSAGE:
                break;

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
        this.kafkaTemplate.send("chat-room-topic", message);
    }

    public void privateProduce(PrivateChatMessage message) {
        this.kafkaTemplate.send("private-chat", message);
    }
}
