package com.example.chatroomskafkabackendproducer.service;

import com.example.chatroomskafkabackendproducer.pojo.UserSocialDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MongoDbService {
    private final MongoTemplate mongoTemplate;

    public void sendFR(String senderId, String receiverId) {
        updateFRAttributes("sent", senderId, receiverId);
        updateFRAttributes("pending", receiverId, senderId);
    }

    public void acceptFR(String acceptorId, String senderId) {
        Query acceptorQuery = new Query(Criteria.where("userId").is(acceptorId));
        UserSocialDetails acceptorDBDetails = mongoTemplate.findOne(acceptorQuery, UserSocialDetails.class);
        acceptorDBDetails.getFriendRequestDetails().getReceived().getPending().remove(senderId);
        acceptorDBDetails.getFriendRequestDetails().getReceived().getAccepted().add(senderId);
        acceptorDBDetails.getFriendIds().add(senderId);

        Update acceptorUpdate = new Update();
        acceptorUpdate.set("friendRequestDetails", acceptorDBDetails.getFriendRequestDetails());
        acceptorUpdate.set("friendIds", acceptorDBDetails.getFriendIds());
        mongoTemplate.upsert(acceptorQuery, acceptorUpdate, UserSocialDetails.class);

        Query senderQuery = new Query(Criteria.where("userId").is(senderId));
        UserSocialDetails senderDBDetails = mongoTemplate.findOne(senderQuery, UserSocialDetails.class);
        senderDBDetails.getFriendRequestDetails().getSent().remove(acceptorId);
        senderDBDetails.getFriendRequestDetails().getReceived().getAccepted().add(acceptorId);
        senderDBDetails.getFriendIds().add(acceptorId);

        Update senderUpdate = new Update();
        senderUpdate.set("friendRequestDetails", senderDBDetails.getFriendRequestDetails());
        senderUpdate.set("friendIds", senderDBDetails.getFriendIds());
        mongoTemplate.upsert(senderQuery, senderUpdate, UserSocialDetails.class);

    }

    private void updateFRAttributes(String key, String userId, String value) {
        Query query = new Query(Criteria.where("userId").is(userId));
        String updateKey = "friendRequestDetails." + (key.equals("sent") ? "sent" : "received.pending");
        Update update = new Update().push(updateKey, value);
        mongoTemplate.findAndModify(query, update, UserSocialDetails.class);
    }
}



