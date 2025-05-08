package org.example.rabbitmqtutorial.domain.user.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreateRequest {
    private String userName;
    private String password;
    private String email;

    @Builder
    public UserCreateRequest(String userName, String password, String email){
        this.userName = userName;
        this.password = password;
        this.email = email;
    }
}
