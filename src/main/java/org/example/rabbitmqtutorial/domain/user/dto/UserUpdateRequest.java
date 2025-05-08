package org.example.rabbitmqtutorial.domain.user.dto;


import lombok.Builder;
import lombok.Data;

@Data
public class UserUpdateRequest {
    private Long userId;
    private String userName;
    private String email;

    @Builder
    public UserUpdateRequest(Long userId, String userName, String email) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
    }
}
