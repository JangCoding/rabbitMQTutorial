package org.example.rabbitmqtutorial.domain.user.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.rabbitmqtutorial.domain.user.dto.UserCreateRequest;
import org.example.rabbitmqtutorial.domain.user.dto.UserResponse;
import org.example.rabbitmqtutorial.domain.user.dto.UserUpdateRequest;
import org.example.rabbitmqtutorial.domain.user.model.User;
import org.example.rabbitmqtutorial.domain.user.repository.UserRepository;
import org.example.rabbitmqtutorial.global.exception.EmailAlreadyExistsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserResponse createUser(UserCreateRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("---Email Already Exists---");
        }

        //User 생성해서 레포지토리에 저장
        User user = User.builder()
                .userName(request.getUserName())
                .password(request.getPassword())
                .email(request.getEmail())
                .build();

//        userRepository.save(user);   // @Transactional로 생략 가능

        return  UserResponse.builder()
                .userName(user.getUserName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build();
    }

    @Override
    public UserResponse getUser(Long userId) {
        User user = userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("---User not found with ID---"));

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
    @Transactional
    public UserResponse updateUser(UserUpdateRequest request){
        User user = userRepository.findUserByUserId(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id"));

        boolean isChanged = false;

        if(request.getUserName() != null && !user.getUserName().equals(request.getUserName())) {
            user.setUserName(request.getUserName());
            isChanged = true;
        }

        if(request.getEmail() != null && !user.getEmail().equals(request.getEmail())) {
            user.setEmail(request.getEmail());
            isChanged = true;
        }

        if(isChanged)
            userRepository.save(user); // @Transactional 있어서 생략 가능

        return UserResponse.from(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findUserByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id"));

        userRepository.delete(user);
    }
}
