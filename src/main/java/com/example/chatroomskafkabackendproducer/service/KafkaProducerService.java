package com.example.chatroomskafkabackendproducer.service;

import com.example.chatroomskafkabackendproducer.pojo.ChatRoomMessage;
import com.example.chatroomskafkabackendproducer.pojo.PrivateChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

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
        CompletableFuture<SendResult<String, Object>> future = this.kafkaTemplate.send("chat-room-topic", message);
        future.whenComplete((res, exception) -> {
            if (exception != null) {
                log.error("*****Failed to send message: {}", exception.getMessage());
            } else {
                log.info("Message was sent Successful : {}", res.getRecordMetadata());
            }
        });

//        future.join();    // this line will make the final thread to wait for future thread to complete, making this synchronous

    }

    public void privateProduce(PrivateChatMessage message) {
        this.kafkaTemplate.send("private-chat", message);
    }
}
