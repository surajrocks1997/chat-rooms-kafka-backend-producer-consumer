package com.example.chatroomskafkabackendproducer.converter;

import com.example.chatroomskafkabackendproducer.pojo.Message;
import org.springframework.kafka.support.serializer.JsonSerde;

public class ChatMessageSerDes extends JsonSerde<Message> {
    public ChatMessageSerDes() {
        super();
        ignoreTypeHeaders();
    }
}
