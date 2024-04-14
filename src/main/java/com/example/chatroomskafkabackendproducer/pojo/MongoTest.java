package com.example.chatroomskafkabackendproducer.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document("mongoTest")
public class MongoTest {

    private String message;
}
