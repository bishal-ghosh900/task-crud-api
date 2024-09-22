package com.example.task_crud_api.model;

import com.example.task_crud_api.exception.TaskApiException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;

@Setter
@Getter
@Builder
public class TaskApiExceptionResponse {
    private int status;
    private String message;
    private long timestamp;

    public static TaskApiExceptionResponse of(TaskApiException exception) {
        return TaskApiExceptionResponse.builder()
                .status(exception.getStatus().value())
                .message(exception.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();
    }

    public static TaskApiExceptionResponse of(AuthenticationException exception) {
        return TaskApiExceptionResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(exception.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();
    }

    public static TaskApiExceptionResponse of(Exception exception) {
        return TaskApiExceptionResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(exception.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();
    }
}
