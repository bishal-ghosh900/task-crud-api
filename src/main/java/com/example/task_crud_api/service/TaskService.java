package com.example.task_crud_api.service;

import com.example.task_crud_api.entity.Task;
import com.example.task_crud_api.entity.User;
import com.example.task_crud_api.exception.TaskException;
import com.example.task_crud_api.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    public TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    public Task findTaskById(int taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isEmpty()) {
            throw new TaskException("Task not found", HttpStatus.BAD_REQUEST);
        }
        return task.get();
    }

    @Transactional
    public Task saveTaskByUserId(int userId, Task task) {
        User user = userService.findUserById(userId);
        task.setUser(user);
        user.addTask(task);
        return taskRepository.save(task);
    }

    @Transactional
    public Task updateTask(int id, Task task) {
        Task optionalTask = findTaskById(id);
        optionalTask.setTask(task.getTask());
        return taskRepository.save(optionalTask);
    }

    @Transactional
    public String deleteTaskById(int taskId) {
        findTaskById(taskId);
        taskRepository.deleteById(taskId);
        return "Task is deleted";
    }

}
