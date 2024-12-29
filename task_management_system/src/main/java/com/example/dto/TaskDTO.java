package com.example.dto;

import com.example.enums.TaskPriority;
import com.example.enums.TaskStatus;
import com.example.model.Task;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDTO {
    private String title;
    private String description;
    private LocalDate dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private TaskStatus status;
    private TaskPriority priority;
    private Long userId;

    public static Task toEntity(TaskDTO taskDTO){
        return Task.builder()
                .title(taskDTO.getTitle())
                .description(taskDTO.getDescription())
                .dueDate(taskDTO.getDueDate())
                .status(taskDTO.getStatus())
                .priority(taskDTO.getPriority())
                .build();
    }

    public static TaskDTO toTaskDTO(Task task){
        return TaskDTO.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .status(task.getStatus())
                .priority(task.getPriority())
                .userId(task.getUser().getId())
                .build();
    }
}
