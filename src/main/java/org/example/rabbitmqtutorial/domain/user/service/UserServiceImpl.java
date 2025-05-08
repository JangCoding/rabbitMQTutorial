package org.example.rabbitmqtutorial.domain.user.service;

import org.example.rabbitmqtutorial.domain.user.dto.UserCreateRequest;
import org.example.rabbitmqtutorial.domain.user.dto.UserResponse;
import org.example.rabbitmqtutorial.domain.user.dto.UserUpdateRequest;
import org.example.rabbitmqtutorial.domain.user.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserResponse createUser(UserCreateRequest request) {

        //User 생성해서 레포지토리에 저장 ㅋㅌㅊㅋㅌㅊㅋㅌ
        User user = User.builder()
                .userName(request.getUserName())
                .password(request.getPassword())
                .email(request.getEmail())
                .build();


        return  UserResponse.builder()
                .userName(user.getUserName())
                .email(user.getEmail())

                .build();
    }

    @Override
    public UserResponse getUser(Long userId) {
        return null;
    }

    @Override
    public UserResponse updateUser(UserUpdateRequest request) {
        return null;
    }

    @Override
    public void deleteUser(Long userId) {

    }
}
