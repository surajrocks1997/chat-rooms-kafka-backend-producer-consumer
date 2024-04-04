package com.example.chatroomskafkabackendproducer.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoomMessage {
    private MessageType messageType;
    private String username;
    private ChatRoomName chatRoomName;
    private String message;
    private String timestamp;
}
