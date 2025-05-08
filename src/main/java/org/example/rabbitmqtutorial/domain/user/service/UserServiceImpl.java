package org.example.rabbitmqtutorial.domain.user.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import org.example.rabbitmqtutorial.domain.user.dto.UserCreateRequest;
import org.example.rabbitmqtutorial.domain.user.dto.UserResponse;
import org.example.rabbitmqtutorial.domain.user.dto.UserUpdateRequest;
import org.example.rabbitmqtutorial.domain.user.model.User;
import org.example.rabbitmqtutorial.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public UserResponse createUser(UserCreateRequest request) {
        //User 생성해서 레포지토리에 저장
        User user = User.builder()
                .userName(request.getUserName())
                .password(request.getPassword())
                .email(request.getEmail())
                .build();

        userRepository.save(user);

        return  UserResponse.builder()
                .userName(user.getUserName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build();
    }

    @Override
    public UserResponse getUser(Long userId) {
        User user = userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id"));

        return UserResponse.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();

    }

    @Override
    public List<UserResponse> getAllUsers(){
        List<User> userList = userRepository.findAll();

        return userList.stream()
                .map(UserResponse::from)    // (user -> UserResponse.from(user)) 와 동일한 문법. 메서드 레퍼런스
                .toList(); // stream() 의 결과로 새로운 List 만들어냄.
    }

    @Override
    public UserResponse updateUser(UserUpdateRequest request){
        User user = userRepository.findUserByUserId(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id"));

        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());

        userRepository.save(user);

        return UserResponse.from(user);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id"));

        userRepository.delete(user);
    }
}
