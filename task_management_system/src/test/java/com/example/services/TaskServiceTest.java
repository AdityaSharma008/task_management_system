package com.example.services;

import com.example.model.Task;
import com.example.model.User;
import com.example.repository.TaskRepository;
import com.example.services.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TaskServiceTest {
    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private static User user;

    @BeforeAll
    public static void createUser(){
        user = User.builder()
                .username("test user")
                .password("testPassword")
                .emailId("testEmail@test.com")
                .build();
    }

    @Test
    public void testCreateTask(){
        Task task = Task.builder()
                .title("Test title")
                .dueDate(LocalDate.of(2100, 12, 12))
                .build();

        given(taskRepository.save(task)).willReturn(task);

        Task savedTask = taskService.createTask(task, user);

        assertThat(savedTask).isNotNull();
        assertThat(savedTask.getTitle()).isEqualTo("Test title");
        assertThat(savedTask.getUser()).isEqualTo(user);
        assertThat(savedTask.getDueDate()).isEqualTo(LocalDate.of(2100, 12, 12));
        verify(taskRepository).save(task);
    }

    @Test
    public void shouldReturnTaskWhenIdExists() {
        Task task = Task.builder()
                .id(1L)
                .title("Test title")
                .user(user)
                .dueDate(LocalDate.of(2100, 12, 12))
                .build();

        given(taskRepository.findById(1L)).willReturn(Optional.of(task));

        Task savedTask = taskService.getTaskById(1L);

        assertThat(savedTask).isNotNull();
        assertThat(savedTask.getId()).isEqualTo(1L);
        assertThat(savedTask.getTitle()).isEqualTo("Test title");
        assertThat(savedTask.getUser()).isEqualTo(user);
        assertThat(savedTask.getDueDate()).isEqualTo(LocalDate.of(2100, 12, 12));
        verify(taskRepository).findById(1L);
    }

    @Test
    public void testGetAllTasks() {
        Task task = Task.builder()
                .title("Test title")
                .user(user)
                .dueDate(LocalDate.of(2100, 12, 12))
                .build();

        Task task1 = Task.builder()
                .title("Test title2")
                .user(user)
                .dueDate(LocalDate.of(2100, 12, 12))
                .build();

        given(taskRepository.findAll()).willReturn(List.of(task, task1));
        var  taskList = taskService.getAllTasks();

        assertThat(taskList).isNotNull();
        assertThat(taskList.size()).isEqualTo(2);
        verify(taskRepository).findAll();
    }

    @Test
    public void testUpdateTask() {
        Task task = Task.builder()
                .id(1L)
                .title("Test title")
                .user(user)
                .dueDate(LocalDate.of(2200, 12, 12))
                .build();

        Task newTask = Task.builder()
                .id(1L)
                .title("Test title2")
                .user(user)
                .dueDate(LocalDate.of(2100, 12, 12))
                .build();

        given(taskRepository.findById(1L)).willReturn(Optional.of(task));
        given(taskRepository.save(newTask)).willReturn(newTask);

        Task updatedTask = taskService.updateTask(1L, newTask);

        assertThat(updatedTask).isNotNull();
        assertThat(updatedTask.getId()).isEqualTo(1L);
        assertThat(updatedTask.getTitle()).isEqualTo("Test title2");
        assertThat(updatedTask.getUser()).isEqualTo(user);
        assertThat(updatedTask.getDueDate()).isEqualTo(LocalDate.of(2100, 12, 12));
        verify(taskRepository).save(newTask);
        verify(taskRepository).findById(1L);
    }

    @Test public void testDeleteTask() {
        Task task = Task.builder()
                .id(1L)
                .title("Test title")
                .user(user)
                .dueDate(LocalDate.of(2200, 12, 12))
                .build();

        given(taskRepository.findById(1L)).willReturn(Optional.of(task));

        taskService.deleteTask(1L);

        verify(taskRepository).findById(1L);
        verify(taskRepository).delete(task);
    }
}