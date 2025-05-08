package org.example.rabbitmqtutorial.domain.user.dto;

import lombok.*;
import org.example.rabbitmqtutorial.domain.user.model.User;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor  // 필요 시 (Jackson 등에서 사용)
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
