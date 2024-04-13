package com.example.chatroomskafkabackendproducer.config;

import com.example.chatroomskafkabackendproducer.pojo.ChatRoomMessage;
import com.example.chatroomskafkabackendproducer.pojo.ChatRoomName;
import com.example.chatroomskafkabackendproducer.pojo.MessageType;
import com.example.chatroomskafkabackendproducer.service.WebSocketSubscriberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class ChatKafkaProcessor {

    private final WebSocketSubscriberService webSocketSubscriberService;
    private final String[] usernames = {
            "suraj.bot@email.com",
            "yash.bot@email.com",
            "simran.bot@email.com",
            "sangamesh.bot@email.com",
            "hitest.bot@email.com",
            "akshat.bot@email.com",
            "shivangi.bot@email.com",
            "divya.bot@email.com",
            "ganesh.bot@email.com",
            "shivam.bot@email.com",
            "kundan.bot@email.com"
    };
    private final String[] messages = {
            "This is a random test message to check word count",
            "Some dummy sample test message",
            "Hey guys, whats up",
            "I am feeling energetic today",
            "I am so tired that i will sleep instantly",
            "We are going out to nearby restaurant for dinner",
            "Lets make this project work today",
            "Yes!!!, This project is finally working",
            "Yay, we made it work",
            "I'm super busy today"
    };

//    @Bean
//    public Supplier<Message<ChatRoomMessage>> producer() {
//        return () -> {
//            ChatRoomName randomChatRoomName = ChatRoomName.values()[new Random().nextInt(ChatRoomName.values().length)];
//            ChatRoomMessage message = new ChatRoomMessage(
//                    MessageType.CHAT_MESSAGE,
//                    usernames[new Random().nextInt(usernames.length)],
//                    randomChatRoomName,
//                    messages[new Random().nextInt(messages.length)],
//                    String.valueOf(System.currentTimeMillis()),
//                    null);
//
//            return MessageBuilder
//                    .withPayload(message)
//                    .build();
//        };
//    }

    @Bean
    public Consumer<KStream<String, ChatRoomMessage>> consumer() {
        return kStream -> kStream
                .peek((key, value) -> log.info("Consumer Data: {} ", value))
                .foreach((key, value) -> webSocketSubscriberService.sendToSubscriber(value, value.getChatRoomName()));
    }
}
