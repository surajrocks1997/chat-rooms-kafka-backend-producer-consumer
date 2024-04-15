package com.example.chatroomskafkabackendproducer.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FriendRequest {
    private List<String> pending;
    private List<String> rejected;
    private List<String> accepted;

    public FriendRequest() {
        this.pending = new ArrayList<>();
        this.rejected = new ArrayList<>();
        this.accepted = new ArrayList<>();

    }
}
