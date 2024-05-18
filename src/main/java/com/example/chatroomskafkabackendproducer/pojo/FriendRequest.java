package com.example.chatroomskafkabackendproducer.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class FriendRequest {
    private Set<String> pending;
    private Set<String> rejected;
    private Set<String> accepted;

    public FriendRequest() {
        this.pending = new HashSet<>();
        this.rejected = new HashSet<>();
        this.accepted = new HashSet<>();

    }
}
