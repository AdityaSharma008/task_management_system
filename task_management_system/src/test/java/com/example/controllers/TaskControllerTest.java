package com.example.controllers;

import com.example.dto.TaskDTO;
import com.example.model.Task;
import com.example.model.User;
import com.example.services.TaskService;
import com.example.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateTaskSuccess() throws Exception{
        TaskDTO taskDTO = TaskDTO.builder()
                .title("Test Task")
                .description("This is test task")
                .dueDate(LocalDate.of(2024, 12, 31))
                .userId(1L)
                .build();

        Task expectedValue = TaskDTO.toEntity(taskDTO);

        Mockito.when(taskService.createTask(Mockito.any(Task.class), Mockito.any(User.class)))
                .thenReturn(expectedValue);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreateTaskValidationError() throws Exception {
        TaskDTO invalidTaskDTO = TaskDTO.builder()
                .description("Missing title")
                .dueDate(LocalDate.of(2024, 12, 31))
                .userId(1L)
                .build();

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidTaskDTO)))
                .andExpect(status().isBadRequest());
    }
}