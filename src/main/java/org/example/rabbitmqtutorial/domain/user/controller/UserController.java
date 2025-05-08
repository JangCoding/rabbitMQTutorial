package org.example.rabbitmqtutorial.domain.user.controller;

import org.example.rabbitmqtutorial.domain.user.dto.UserCreateRequest;
import org.example.rabbitmqtutorial.domain.user.dto.UserResponse;
import org.example.rabbitmqtutorial.domain.user.dto.UserUpdateRequest;
import org.example.rabbitmqtutorial.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService)  // @RequiredArgsConstructor 로 대체 가능
    {
        this.userService = userService;
    }

    @PostMapping
    public UserResponse createUser(@RequestBody UserCreateRequest request){
        return userService.createUser(request);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long userId) {
        UserResponse userResponse = userService.getUser(userId);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> userResponseList = userService.getAllUsers();
        return ResponseEntity.ok(userResponseList);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserUpdateRequest request)
    {
        UserResponse userResponse = userService.updateUser(request);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();     // HTTP 204
    }
}
