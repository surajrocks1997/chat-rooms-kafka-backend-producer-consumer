package com.example.chatroomskafkabackendproducer.service;

import com.example.chatroomskafkabackendproducer.pojo.ChatRoomMessage;
import com.example.chatroomskafkabackendproducer.pojo.PrivateChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    private final WebSocketSubscriberService webSocketSubscriberService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "private-chat", containerFactory = "messageFactory")
    public void consumePrivate(@Payload Object entity) {
        log.info("I am here from private.");
        PrivateChatMessage message = objectMapper.convertValue((((ConsumerRecord<?, ?>) entity).value()), PrivateChatMessage.class);
        webSocketSubscriberService.sendToUser(message);
    }

    @KafkaListener(topics = "chat-room-topic", containerFactory = "messageFactory")
    public void consume(@Payload Object entity) {
        log.info("I am here from public.");
        ChatRoomMessage message = objectMapper.convertValue((((ConsumerRecord<?, ?>) entity).value()), ChatRoomMessage.class);
        webSocketSubscriberService.sendToSubscriber(message, message.getChatRoomName());
    }


}
