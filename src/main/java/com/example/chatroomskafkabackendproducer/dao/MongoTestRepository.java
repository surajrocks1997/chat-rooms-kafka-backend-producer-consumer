package com.example.chatroomskafkabackendproducer.dao;

import com.example.chatroomskafkabackendproducer.pojo.MongoTest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoTestRepository extends MongoRepository<MongoTest, String> {
}
