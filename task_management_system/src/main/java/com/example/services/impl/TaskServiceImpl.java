package com.example.services.impl;

import com.example.model.Task;
import com.example.model.Users;
import com.example.repository.TaskRepository;
import com.example.services.AuthService;
import com.example.services.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository repository;
    private final AuthService authService;
    public TaskServiceImpl(TaskRepository repository, AuthService authService){
        this.repository = repository;
        this.authService = authService;
    }

    @Override
    public Task createTask(Task task, Users user){
        task.setUser(user);
        return repository.save(task);
    }

    @Override
    public Task getTaskById(Long id){
        return repository.findById(id).orElseThrow(
                () -> new RuntimeException("Task not found")
        );
    }

    @Override
    public List<Task> getAllTasks(){
        return repository.findAll();
    }

    @Override
    public Task updateTask(Long id, Task task){
        Task existingTask = getTaskById(id);
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        return repository.save(existingTask);
    }

    @Override
    public void deleteTask(Long id){
        repository.delete(getTaskById(id));
    }

    @Override
    public List<Task> getTasksForCurrentUser() {
        Users loggedInUser = authService.getLoggedInUser();
        return repository.findByUserId(loggedInUser.getId());
    }
}
