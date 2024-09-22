package com.example.task_crud_api.controller;

import com.example.task_crud_api.entity.User;
import com.example.task_crud_api.service.UserService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAllUser() {
        return userService.findAllUser();
    }

    @GetMapping("/{userId}")
    public User findUserByUsername(
            @PathVariable int userId,
            @RequestParam(name="page", defaultValue = "1") Integer pageNumber,
            @RequestParam(name = "count", defaultValue = "10") Integer maxCount
    ) {
        return userService.findUserWithTasksByUsername(userId, pageNumber, maxCount);
    }
}
