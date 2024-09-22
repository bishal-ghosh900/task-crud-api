package com.example.task_crud_api.controller;

import com.example.task_crud_api.entity.User;
import com.example.task_crud_api.service.AuthenticationService;

import com.example.task_crud_api.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authService;
    private final UserService userService;

    public AuthenticationController(AuthenticationService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody User user)  {
        return authService.registerUser(user);
    }

    @GetMapping("/me")
    public User currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return userService.findUserById(user.getId());
    }

    @PostMapping("/login")
    public User loginUser(@RequestBody User user) {
        return authService.loginUser(user);
    }

}
