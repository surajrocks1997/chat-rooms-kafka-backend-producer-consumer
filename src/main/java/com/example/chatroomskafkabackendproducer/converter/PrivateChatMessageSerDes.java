package com.example.chatroomskafkabackendproducer.converter;

import com.example.chatroomskafkabackendproducer.pojo.ChatRoomMessage;
import com.example.chatroomskafkabackendproducer.pojo.PrivateChatMessage;
import org.springframework.kafka.support.serializer.JsonSerde;

public class PrivateChatMessageSerDes extends JsonSerde<PrivateChatMessage> {
    public PrivateChatMessageSerDes() {
        super();
        ignoreTypeHeaders();
    }
}
