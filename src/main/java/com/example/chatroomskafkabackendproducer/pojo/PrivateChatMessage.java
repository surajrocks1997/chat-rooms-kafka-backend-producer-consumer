package com.example.chatroomskafkabackendproducer.pojo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PrivateChatMessage {
    private MessageType messageType;
    private String username;
    private String receiver;
    private String message;
    private String timestamp;

}
