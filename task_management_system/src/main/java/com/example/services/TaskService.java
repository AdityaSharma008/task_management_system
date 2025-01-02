package com.example.services;

import com.example.model.Task;
import com.example.model.Users;

import java.util.List;

public interface TaskService {
    Task createTask(Task task, Users user);
    Task getTaskById(Long id);
    List<Task> getAllTasks();
    Task updateTask(Long id, Task task);
    void deleteTask(Long id);
    List<Task> getTasksForCurrentUser();
}
