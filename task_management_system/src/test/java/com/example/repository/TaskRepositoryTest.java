package com.example.repository;

import com.example.model.Task;
import com.example.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TaskRepositoryTest {
    private User user;
    private Task task;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TaskRepository taskRepository;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUsername("user");
        user.setPassword("password");
        user.setEmailId("testUser@test.com");

        task = new Task();
        task.setTitle("test title");
        task.setUser(user);
        task.setDueDate(LocalDate.of(2100, 12, 12));

        userRepository.save(user);
        taskRepository.save(task);
    }

    @AfterEach
    public void tearDown() {
        taskRepository.delete(task);
        userRepository.delete(user);
    }

    @Test
    void givenTaskWhenSavedThenCanBeFoundById() {
        Task savedTask = taskRepository.findById(task.getId()).orElse(null);
        assertNotNull(savedTask);
        assertEquals(task.getTitle(), savedTask.getTitle());
        assertEquals(task.getUser(), savedTask.getUser());
        assertEquals(task.getDueDate(), savedTask.getDueDate());
    }
}