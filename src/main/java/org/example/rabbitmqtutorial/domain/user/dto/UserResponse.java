package org.example.rabbitmqtutorial.domain.user.dto;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;
import org.example.rabbitmqtutorial.domain.user.model.User;

import java.time.LocalDateTime;

@Data   // record UserResponse(...) 으로 대체, 생략 가능
public class UserResponse {
    private Long userId;
    private String userName;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public UserResponse(Long userId, String userName, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
    }

    public static UserResponse from(User user)
    {
        return UserResponse.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    // 생성자, getter, setter, equal, hashcode, toString 필요
}
