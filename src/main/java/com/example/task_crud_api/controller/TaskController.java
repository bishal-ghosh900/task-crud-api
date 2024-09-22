package com.example.task_crud_api.controller;

import com.example.task_crud_api.entity.Task;
import com.example.task_crud_api.entity.User;
import com.example.task_crud_api.service.AuthenticationService;
import com.example.task_crud_api.service.TaskService;
import com.example.task_crud_api.service.UserService;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;

    public TaskController(UserService userService, TaskService taskService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping
    public List<Task> findAllTasks(
            @RequestParam(name="page", defaultValue = "1") Integer pageNumber,
            @RequestParam(name = "count", defaultValue = "10") Integer maxCount
    ) {
        return userService.findUserWithTasksByUserId(pageNumber, maxCount).getTasks();
    }

    @GetMapping("/{taskId}")
    public Task findTaskById(@PathVariable int taskId) {
        return taskService.findTaskById(taskId);
    }

    @PostMapping
    public Task saveTask(@RequestBody Task task) {
        return taskService.saveTaskByUserId(task);
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
