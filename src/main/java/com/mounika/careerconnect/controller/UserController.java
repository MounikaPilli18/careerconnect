package com.mounika.careerconnect.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mounika.careerconnect.entity.User;
import com.mounika.careerconnect.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {

        return userService.registerUser(
                user.getUsername(),
                user.getPassword(),
                user.getRole()
        );
    }
}