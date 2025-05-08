package org.example.rabbitmqtutorial.domain.user.service;

import org.example.rabbitmqtutorial.domain.user.dto.UserCreateRequest;
import org.example.rabbitmqtutorial.domain.user.dto.UserResponse;
import org.example.rabbitmqtutorial.domain.user.dto.UserUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    public UserResponse createUser(UserCreateRequest request);
    public UserResponse getUser(Long userId);
    public List<UserResponse> getAllUsers();
    public UserResponse updateUser(UserUpdateRequest request);
    public void deleteUser(Long userId);

}
