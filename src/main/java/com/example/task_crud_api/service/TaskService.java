package com.example.task_crud_api.service;

import com.example.task_crud_api.entity.Task;
import com.example.task_crud_api.entity.User;
import com.example.task_crud_api.exception.TaskException;
import com.example.task_crud_api.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        int userId = getCurrentUserId();
        Optional<Task> task = taskRepository.findByUserIdAndTaskId(taskId, userId);
        if (task.isEmpty()) {
            throw new TaskException("Task not found", HttpStatus.BAD_REQUEST);
        }
        return task.get();
    }

    @Transactional
    public Task saveTaskByUserId(Task task) {
        int userId = getCurrentUserId();
        User user = userService.findUserById(userId);
        task.setUser(user);
        user.addTask(task);
        return taskRepository.save(task);
    }

    @Transactional
    public Task updateTask(int taskId, Task task) {
        int userId = getCurrentUserId();
        Optional<Task> optionalTask = taskRepository.findByUserIdAndTaskId(taskId, userId);
        if(optionalTask.isEmpty()) {
            throw new TaskException("Task not found", HttpStatus.BAD_REQUEST);
        }
        optionalTask.get().setTask(task.getTask());
        return taskRepository.save(optionalTask.get());
    }

    @Transactional
    public String deleteTaskById(int taskId) {
        int userId = getCurrentUserId();
        Optional<Task> optionalTask = taskRepository.findByUserIdAndTaskId(taskId, userId);
        if(optionalTask.isEmpty()) {
            throw new TaskException("Task not found", HttpStatus.BAD_REQUEST);
        }
        taskRepository.deleteById(optionalTask.get().getId());
        return "Task is deleted";
    }

    public int getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user.getId();
    }

}
