package com.example.task_crud_api.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends TaskException {
    public UserNotFoundException(String message, HttpStatus status) {
        super(message, status);
    }
}
