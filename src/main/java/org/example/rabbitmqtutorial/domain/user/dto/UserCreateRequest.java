package org.example.rabbitmqtutorial.domain.user.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class UserCreateRequest {
    @NotNull(message = "UserName cannot be NULL")
    private String userName;

    @NotNull(message = "Password cannot be NULL")
    private String password;

    @NotNull(message = "Email cannot be NULL")
    @Email(message = "Invalid Email Format")
    private String email;

    public UserCreateRequest(String userName, String password, String email){
        this.userName = userName;
        this.password = password;
        this.email = email;
    }
}
