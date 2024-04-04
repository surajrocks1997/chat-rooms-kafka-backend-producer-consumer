package com.example.chatroomskafkabackendproducer.config;

import com.example.chatroomskafkabackendproducer.pojo.MessageType;
import com.example.chatroomskafkabackendproducer.pojo.UserPresence;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageTemplate;

    @EventListener
    public void handleWebSocketUserPresenceDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String email = (String) headerAccessor.getSessionAttributes().get("email");
        String chatRoomName = (String) headerAccessor.getSessionAttributes().get("chatRoomName");
        UserPresence userPresence = UserPresence
                .builder()
                .presenceType(MessageType.USER_OFFLINE)
                .email(email)
                .build();

        log.info("From Disconnect Event******************");
        log.info(userPresence.toString());
        log.info(chatRoomName);

        messageTemplate.convertAndSend("/topic/chatRoom." + chatRoomName, userPresence);
    }
}
