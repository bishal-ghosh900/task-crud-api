package com.example.task_crud_api.configuration;

import com.example.task_crud_api.exception.TaskApiException;
import com.example.task_crud_api.exception.TaskException;
import com.example.task_crud_api.exception.UserAuthenticationException;
import com.example.task_crud_api.exception.UserNotFoundException;
import com.example.task_crud_api.model.TaskApiExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        TaskApiExceptionResponse response = new TaskApiExceptionResponse();
        response.setStatus(exception.getStatus().value());
        response.setMessage(exception.getMessage());
        response.setTimestamp(System.currentTimeMillis());
        return ResponseEntity.status(exception.getStatus()).body(response);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<TaskApiExceptionResponse> handleTaskExceptions(Exception exception) {
        TaskApiExceptionResponse response = new TaskApiExceptionResponse();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMessage(exception.getMessage());
        response.setTimestamp(System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}
