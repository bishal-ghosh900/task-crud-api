package com.example.task_crud_api.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TaskApiExceptionResponse {
    private int status;
    private String message;
    private long timestamp;
}
