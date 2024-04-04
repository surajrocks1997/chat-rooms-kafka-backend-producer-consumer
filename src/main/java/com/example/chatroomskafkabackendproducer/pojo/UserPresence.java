package com.example.chatroomskafkabackendproducer.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPresence {
    private MessageType presenceType;
    private String name;
    private String email;
}
