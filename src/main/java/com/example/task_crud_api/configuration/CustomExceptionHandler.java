package com.example.task_crud_api.configuration;

import com.example.task_crud_api.exception.TaskApiException;
import com.example.task_crud_api.exception.TaskException;
import com.example.task_crud_api.exception.UserAuthenticationException;
import com.example.task_crud_api.exception.UserNotFoundException;
import com.example.task_crud_api.model.TaskApiExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({
            UserAuthenticationException.class,
            UserNotFoundException.class,
            TaskException.class
    })
    public ResponseEntity<TaskApiExceptionResponse> handleTaskApiExceptions(TaskApiException exception) {
        return ResponseEntity.status(exception.getStatus())
                .body(TaskApiExceptionResponse.of(exception));
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<TaskApiExceptionResponse> handleAuthenticationExceptions(AuthenticationException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(TaskApiExceptionResponse.of(exception));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<TaskApiExceptionResponse> handleTaskExceptions(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(TaskApiExceptionResponse.of(exception));
    }

}
