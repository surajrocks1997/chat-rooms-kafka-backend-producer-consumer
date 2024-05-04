package com.example.chatroomskafkabackendproducer.service;

import com.example.chatroomskafkabackendproducer.pojo.UserSocialDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DBService {
    private final MongoTemplate mongoTemplate;

    public void sendFR(String senderId, String receiverId) {
        updateFRAttributes("sent", senderId, receiverId);
        updateFRAttributes("pending", receiverId, senderId);
    }

    private void updateFRAttributes(String key, String userId, String value) {
        Query query = new Query(Criteria.where("userId").is(userId));
        String updateKey = "friendRequestDetails." + (key.equals("sent") ? "sent" : "received.pending");
        Update update = new Update().push(updateKey, value);
        mongoTemplate.findAndModify(query, update, UserSocialDetails.class);
    }
}



