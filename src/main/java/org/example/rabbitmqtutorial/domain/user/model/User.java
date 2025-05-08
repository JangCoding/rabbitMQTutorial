package org.example.rabbitmqtutorial.domain.user.model;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userName;
    private String password;
    private String email;

    @CreationTimestamp  // 생성시각 자동 설정
    private LocalDateTime createdAt;

    @UpdateTimestamp    // 수정시각 자동 설정
    private LocalDateTime updatedAt;

    @Builder    // 빌더 패턴
    public User(String userName, String password, String email){
        this.userName = userName;
        this.password = password;
        this.email = email;
    }
}
