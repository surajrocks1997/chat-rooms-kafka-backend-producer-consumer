package com.example.chatroomskafkabackendproducer.service;

import com.example.chatroomskafkabackendproducer.pojo.ChatRoomMessage;
import com.example.chatroomskafkabackendproducer.pojo.ChatRoomName;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketSubscriberService {

    private final SimpMessageSendingOperations messageTemplate;

    public void sendToSubscriber(ChatRoomMessage message, ChatRoomName chatRoomName) {
        this.messageTemplate.convertAndSend("/topic/chatRoom." + chatRoomName.toString(), message);
    }
}
