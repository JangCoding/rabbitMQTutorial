package org.example.rabbitmqtutorial.domain.user.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.rabbitmqtutorial.domain.user.dto.UserCreateRequest;
import org.example.rabbitmqtutorial.domain.user.dto.UserResponse;
import org.example.rabbitmqtutorial.domain.user.dto.UserUpdateRequest;
import org.example.rabbitmqtutorial.domain.user.model.User;
import org.example.rabbitmqtutorial.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    public UserRepository userRepository;

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
    public UserResponse updateUser(UserUpdateRequest request){
        User user = userRepository.findUserByUserId(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id"));

        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());

        userRepository.save(user);

        return UserResponse.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id"));

        userRepository.delete(user);
    }
}
