package com.example.task_crud_api.exception;

import org.springframework.http.HttpStatus;

public class TaskException extends TaskApiException {
    public TaskException(String message, HttpStatus status) {
        super(message, status);
    }
}
