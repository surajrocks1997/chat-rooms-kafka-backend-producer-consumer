package com.example.chatroomskafkabackendproducer.service;

import com.example.chatroomskafkabackendproducer.pojo.ChatRoomName;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
@Data
public class UserPresenceHandler {

    private final Map<ChatRoomName, Set<String>> userPresence;

    public Set<String> addPresenceToMap(ChatRoomName chatRoomName, String email) {
        Set<String> presence = this.userPresence.getOrDefault(chatRoomName, new HashSet<>());
        presence.add(email);
        userPresence.put(chatRoomName, presence);

        return userPresence.get(chatRoomName);
    }

    public Set<String> removePresenceFromMap(ChatRoomName chatRoomName, String email) {
        Set<String> newPresence = this.userPresence.get(chatRoomName);
        newPresence.remove(email);
        userPresence.put(chatRoomName, newPresence);
        return userPresence.get(chatRoomName);
    }

    public Set<String> getUserPresenceInChatRoom(ChatRoomName chatRoomName) {
        return this.userPresence.get(chatRoomName);
    }

}
