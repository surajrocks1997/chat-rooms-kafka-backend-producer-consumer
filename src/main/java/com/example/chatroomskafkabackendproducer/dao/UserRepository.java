package com.example.chatroomskafkabackendproducer.dao;

import com.example.chatroomskafkabackendproducer.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
