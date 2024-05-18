package com.example.chatroomskafkabackendproducer.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class FriendRequestDetails {
    private FriendRequest received;
    private Set<String> sent;

    public FriendRequestDetails() {
        this.received = new FriendRequest();
        this.sent = new HashSet<>();
    }
}
