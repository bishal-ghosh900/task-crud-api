package com.example.task_crud_api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class TaskApiException extends RuntimeException {
    private String message;
    private HttpStatus status;

    public TaskApiException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }
}
