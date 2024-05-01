package com.example.chatroomskafkabackendproducer.pojo;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("user-social-details-collection")
public class UserSocialDetails {
    @Id
    private String userId;
    private List<String> friendIds;
    private FriendRequestDetails friendRequestDetails;
    private long createdAt;
    private long modifiedAt;

    public UserSocialDetails(String userId) {
        this.userId = userId;
        this.friendIds = new ArrayList<>();
        this.friendRequestDetails = new FriendRequestDetails();
        this.createdAt = System.currentTimeMillis();
        this.modifiedAt = System.currentTimeMillis();
    }
}
