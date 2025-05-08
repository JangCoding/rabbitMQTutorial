package org.example.rabbitmqtutorial.domain.user.controller;

import org.example.rabbitmqtutorial.domain.user.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;
}
