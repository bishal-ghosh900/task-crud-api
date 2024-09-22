package com.example.task_crud_api.exception;

import org.springframework.http.HttpStatus;

public class UserAuthenticationException extends TaskApiException {

    public UserAuthenticationException(String message, HttpStatus status) {
        super(message, status);
    }
}
