package com.example.chatroomskafkabackendproducer.converter;

import com.example.chatroomskafkabackendproducer.pojo.ChatRoomMessage;
import org.springframework.kafka.support.serializer.JsonSerde;

public class ChatMessageSerDes extends JsonSerde<ChatRoomMessage> {
    public ChatMessageSerDes() {
        super();
        ignoreTypeHeaders();
    }
}
