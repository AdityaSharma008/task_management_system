package com.example.controllers;

import com.example.dto.TaskDTO;
import com.example.model.Task;
import com.example.model.User;
import com.example.services.TaskService;
import com.example.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;

    public TaskController(TaskService taskService, UserService userService){
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping
    public List<TaskDTO> getAllTasks(){
        return taskService.getAllTasks().stream()
                .map(TaskDTO::toTaskDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        Task requestedTask = taskService.getTaskById(id);
        TaskDTO responseTaskDTO = TaskDTO.toTaskDTO(requestedTask);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseTaskDTO);
    }

    //todo: change provided argument to user id instead of user
    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO){
        User taskUser = userService.getUserById(taskDTO.getUserId());
        Task createdTask = TaskDTO.toEntity(taskDTO);

        taskService.createTask(createdTask, taskUser);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO){
        Task task = TaskDTO.toEntity(taskDTO);
        Task updatedTask = taskService.updateTask(id, task);
        TaskDTO updatedTaskDTO = TaskDTO.toTaskDTO(updatedTask);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedTaskDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
