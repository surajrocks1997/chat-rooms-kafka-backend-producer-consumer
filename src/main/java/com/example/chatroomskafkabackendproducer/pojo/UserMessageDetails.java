package com.example.chatroomskafkabackendproducer.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@Document("user-message-details-collection")
public class UserMessageDetails {

    private String room;
    private String message;
    private Date timestamp;
    private Seen seen;
    private String senderId;
    private String receiverId;
    private Date createdAt;
    private Date modifiedAt;
}
