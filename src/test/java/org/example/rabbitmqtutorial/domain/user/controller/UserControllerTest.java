package org.example.rabbitmqtutorial.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.example.rabbitmqtutorial.domain.user.dto.UserCreateRequest;
import org.example.rabbitmqtutorial.domain.user.dto.UserResponse;
import org.example.rabbitmqtutorial.domain.user.dto.UserUpdateRequest;
import org.example.rabbitmqtutorial.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)  // 컨트롤러만 테스트
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // JSON 변환용

    @MockBean
    private UserService userService; // 의존하는 서비스 계층은 mock으로 주입


    // 이후에 테스트 메서드 작성 예정
    @Test
    @DisplayName("회원 생성 성공")
    void createUser_success() throws Exception {
        // Given
        UserCreateRequest req = new UserCreateRequest("john", "john@example.com", "password123");

        UserResponse res = UserResponse.builder()
                .userId(1L)
                .userName("john")
                .email("john@example.com")
                .createdAt(LocalDateTime.now())
                .build();

        // 서비스 계층이 예상한 응답을 반환하도록 설정
        given(userService.createUser(any(UserCreateRequest.class))).willReturn(res);

        // When & Then
        mockMvc.perform(post("/user")  // @PostMapping 주소로 요청 보냄
                        .contentType(MediaType.APPLICATION_JSON)    // 요청 본문 타입 명시. application/json
                        .content(objectMapper.writeValueAsString(req))  // req 객체를 JSON 문자열로 변환하여 요청 본문에 넣기
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.userName").value("john"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    @DisplayName("단일 조회 성공")
    void getUser_success() throws Exception {
        //given
        UserResponse res = UserResponse.builder()
                .userId(1L)
                .userName("jang")
                .createdAt(LocalDateTime.now())
                .email("jang@email.com")
                .build();

        given(userService.getUser(1L)).willReturn(res);

        //when&then
        mockMvc.perform(get("/user/{userId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.userName").value("jang"))
                .andExpect(jsonPath("$.email").value("jang@email.com"));

    }

    @Test
    @DisplayName("단일 조회 실패-해당 ID 에 유저 없음")
    void getUser_fail_entityNotFound() throws Exception {
        //given
        given(userService.getUser(1L)).willThrow(new EntityNotFoundException());

        //when&then
        mockMvc.perform(get("/user/{userId}", 1L))
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("전체 조회 성공")
    void getUserAll_success() throws Exception {
        //given
        UserResponse user1 = UserResponse.builder()
                .userId(1L)
                .userName("jang")
                .createdAt(LocalDateTime.now())
                .email("jang@email.com")
                .build();

        UserResponse user2 = UserResponse.builder()
                .userId(2L)
                .userName("bang")
                .createdAt(LocalDateTime.now())
                .email("bang@email.com")
                .build();

        List<UserResponse> userResponseList = List.of(user1, user2);


        given(userService.getAllUsers()).willReturn(userResponseList);

        //when&then
        mockMvc.perform(get("/user/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));

    }


    @Test
    @DisplayName("업데이트 성공")
    void updateUser_success() throws Exception {
        //given
        UserUpdateRequest req = UserUpdateRequest.builder()
                .userId(1L)
                .userName("jang")
                .email("jang@email.com")
                .build();

        UserResponse res = UserResponse.builder()
                .userId(1L)
                .userName("jang")
                .email("jang@email.com")
                .createdAt(LocalDateTime.now())
                .build();


        given(userService.updateUser(any(UserUpdateRequest.class))).willReturn(res);

        //when & then
        mockMvc.perform(put("/user/{userID}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.userName").value("jang"))
                .andExpect(jsonPath("$.email").value("jang@email.com"));
    }

    @Test
    @DisplayName("업데이트 실패 - 해당 ID 에 유저가 없음")
    void updateUser_fail_entityNotFound() throws Exception {
        //given
        UserUpdateRequest req = UserUpdateRequest.builder()
                .userId(1L)
                .userName("jang")
                .email("jang@email.com")
                .build();

        given(userService.updateUser(any(UserUpdateRequest.class)))
                .willThrow(new EntityNotFoundException());

        //when & then
        mockMvc.perform(put("/user/{userID}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("삭제 실패 - 해당 ID 에 유저가 없음")
    void deleteUser_fail_entityNotFound() throws Exception {
        //given
        UserUpdateRequest req = UserUpdateRequest.builder()
                .userId(1L)
                .userName("jang")
                .email("jang@email.com")
                .build();

        doThrow(new EntityNotFoundException())      // 반환형이 없을 땐 given 사용 불가
                .when(userService)
                .deleteUser(1L);


        //when & then
        mockMvc.perform(delete("/user/{userID}", 1L))
                .andExpect(status().isNotFound());
    }
}