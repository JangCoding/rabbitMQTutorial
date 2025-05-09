package org.example.rabbitmqtutorial.domain.user.service;


import org.example.rabbitmqtutorial.domain.user.dto.UserCreateRequest;
import org.example.rabbitmqtutorial.domain.user.dto.UserResponse;
import org.example.rabbitmqtutorial.domain.user.model.User;
import org.example.rabbitmqtutorial.domain.user.repository.UserRepository;
import org.example.rabbitmqtutorial.global.exception.EmailAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("성공적으로 사용자를 생성한다")
    void createUser_success(){
        //Given
        UserCreateRequest request = new UserCreateRequest("testUser", "123123", "test@test.com");
        given(userRepository.existsByEmail(request.getEmail())).willReturn(false);
        given(userRepository.save(any(User.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        //When
        UserResponse response = userServiceImpl.createUser(request);

        //Then
        assertEquals("testUser", response.getUserName());
        assertEquals("test@test.com", response.getEmail());
        verify(userRepository).save(any(User.class));   // save 호출 확인

    }

    @Test
    @DisplayName("사용자 생성실패 - 이메일 중복")
    void createUser_fail_EmailAlreadyExists(){
        //Given
        UserCreateRequest request = new UserCreateRequest("testUser", "123123", "test@test.com");
        given(userRepository.existsByEmail(request.getEmail())).willReturn(true);

        //When
//        UserResponse response = userServiceImpl.createUser(request); // assertThatThrownBy 에서 호출

        //Then
        //assertThrows() 보다 더 많은 예외속성 검증 가능.
        assertThatThrownBy(() -> userServiceImpl.createUser(request))
                .isInstanceOf(EmailAlreadyExistsException.class)
                .hasMessageContaining("---Email Already Exists---");

        // userRepository.save()가 호출되지 않았는지 검증
        then(userRepository).should(never()).save(any(User.class));

    }
//    @Test
//    @DisplayName("성공적으로 사용자를 생성한다")
//    void createUser_success(){
//        //Given
//
//
//        //When
//
//
//        //Then
//    }
}
