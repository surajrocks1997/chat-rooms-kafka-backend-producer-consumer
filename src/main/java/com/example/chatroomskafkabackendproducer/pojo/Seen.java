package com.example.chatroomskafkabackendproducer.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Seen {
    private boolean status;
    private Date seenAt;

}
