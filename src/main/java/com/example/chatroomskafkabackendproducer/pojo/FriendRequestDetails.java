package com.example.chatroomskafkabackendproducer.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FriendRequestDetails {
    private FriendRequest received;
    private List<String> sent;

    public FriendRequestDetails() {
        this.received = new FriendRequest();
        this.sent = new ArrayList<>();
    }
}
