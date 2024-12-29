package com.example.services;

import com.example.model.Task;
import com.example.model.User;

import java.util.List;

public interface TaskService {
    Task createTask(Task task, User user);
    Task getTaskById(Long id);
    List<Task> getAllTasks();
    Task updateTask(Long id, Task task);
    void deleteTask(Long id);
}
