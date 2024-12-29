package com.example.services.impl;

import com.example.model.Task;
import com.example.model.User;
import com.example.repository.TaskRepository;
import com.example.services.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository repository;
    public TaskServiceImpl(TaskRepository repository){
        this.repository = repository;
    }

    @Override
    public Task createTask(Task task, User user){
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
}
