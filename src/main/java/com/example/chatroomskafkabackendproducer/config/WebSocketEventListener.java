package com.example.chatroomskafkabackendproducer.config;

import com.example.chatroomskafkabackendproducer.pojo.ChatRoomMessage;
import com.example.chatroomskafkabackendproducer.pojo.ChatRoomName;
import com.example.chatroomskafkabackendproducer.pojo.MessageType;
import com.example.chatroomskafkabackendproducer.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageTemplate;
    private final KafkaProducerService kafkaProducerService;

    @EventListener
    public void handleWebSocketUserPresenceDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        String chatRoomName = (String) headerAccessor.getSessionAttributes().get("chatRoomName");

        if (chatRoomName != null) {
            try {
                ChatRoomMessage message = ChatRoomMessage
                        .builder()
                        .messageType(MessageType.USER_OFFLINE)
                        .username(username)
                        .chatRoomName(ChatRoomName.valueOf(chatRoomName))
                        .build();

                log.info("From Disconnect Event******************");
                log.info(message.toString());
                log.info(chatRoomName);

                kafkaProducerService.produce(message);
            } catch (Exception e) {
                log.error("Caught Error At SessionDisconnectEvent: {}", headerAccessor);
                throw new RuntimeException(e);
            }
        }
    }
}
