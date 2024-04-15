package com.example.chatroomskafkabackendproducer.dao;

import com.example.chatroomskafkabackendproducer.pojo.UserSocialDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserSocialDetailsRepository extends MongoRepository<UserSocialDetails, String> {

    Optional<UserSocialDetails> findByUserId(String userId);
}
