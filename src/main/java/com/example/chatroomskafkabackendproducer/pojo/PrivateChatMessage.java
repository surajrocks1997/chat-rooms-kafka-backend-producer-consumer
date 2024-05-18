package com.example.chatroomskafkabackendproducer.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrivateChatMessage {
    private MessageType messageType;
    private String username;
    private String receiver;
    private String message;
    private String timestamp;

}
