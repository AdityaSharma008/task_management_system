package com.example.services;

import com.example.model.Task;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    public void testCreateUser(){
        User user = User.builder()
                .username("test user")
                .password("testPassword")
                .emailId("testEmail@test.com")
                .build();

        given(userRepository.save(user)).willReturn(user);

        User savedUser = userService.createUser(user);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("test user");
        assertThat(savedUser.getPassword()).isEqualTo("testPassword");
        assertThat(savedUser.getEmailId()).isEqualTo("testEmail@test.com");
        verify(userRepository).save(user);
    }

    @Test
    public void shouldReturnUserWhenIdExists(){
        User user = User.builder()
                .id(1L)
                .username("test user")
                .password("testPassword")
                .emailId("testEmail@test.com")
                .build();

        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        User savedUser = userService.getUserById(1L);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isEqualTo(1L);
        assertThat(savedUser.getUsername()).isEqualTo("test user");
        assertThat(savedUser.getPassword()).isEqualTo("testPassword");
        assertThat(savedUser.getEmailId()).isEqualTo("testEmail@test.com");
        verify(userRepository).findById(1L);
    }

    @Test
    public void testGetAllUsers(){
        User user = User.builder()
                .username("test user")
                .password("testPassword")
                .emailId("testEmail@test.com")
                .build();

        User user1 = User.builder()
                .username("test user2")
                .password("testPassword2")
                .emailId("testEmail2@test.com")
                .build();

        given(userRepository.findAll()).willReturn(List.of(user, user1));

        var userList = userService.getAllUsers();

        assertThat(userList).isNotNull();
        assertThat(userList.size()).isEqualTo(2);
        verify(userRepository).findAll();
    }

    @Test
    public void testDeleteUser(){
        User user = User.builder()
                .id(1L)
                .username("test user")
                .password("testPassword")
                .emailId("testEmail@test.com")
                .build();

        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository).findById(1L);
        verify(userRepository).delete(user);
    }
}