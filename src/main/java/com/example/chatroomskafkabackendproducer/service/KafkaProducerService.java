package com.example.chatroomskafkabackendproducer.service;

import com.example.chatroomskafkabackendproducer.pojo.ChatRoomMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final StreamBridge streamBridge;

    public void produce(ChatRoomMessage message) {
        Message<ChatRoomMessage> build = MessageBuilder.withPayload(message).build();
        streamBridge.send("producer-out-0", build);

    }
}
