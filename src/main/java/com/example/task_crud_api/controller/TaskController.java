package com.example.task_crud_api.controller;

import com.example.task_crud_api.entity.Task;
import com.example.task_crud_api.service.TaskService;
import com.example.task_crud_api.service.UserService;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(UserService userService, TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{taskId}")
    public Task findTaskById(@PathVariable int taskId) {
        return taskService.findTaskById(taskId);
    }

    @PostMapping("/user/{userId}")
    public Task saveTask(@PathVariable int userId, @RequestBody Task task) {
        return taskService.saveTaskByUserId(userId, task);
    }

    @PatchMapping("/{taskId}")
    public Task updateTask(
            @PathVariable int taskId,
            @RequestBody Task task
    ) {
        return taskService.updateTask(taskId, task);
    }

    @DeleteMapping("/{taskId}")
    public String deleteTaskById(@PathVariable int taskId) {
        return taskService.deleteTaskById(taskId);
    }

}
