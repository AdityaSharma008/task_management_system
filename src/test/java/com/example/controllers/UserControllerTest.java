package com.example.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.example.config.TestSecurityConfig;
import com.example.exceptions.ResourceNotFoundException;
import com.example.model.Users;
import com.example.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest(UserController.class)
@Import(TestSecurityConfig.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateUserSuccess() throws Exception {
        Users user = Users.builder().username("test user").password("testPassword").emailId("testEmail@test.com").build();

        Mockito.when(userService.createUser(Mockito.any(Users.class))).thenReturn(user);

        mockMvc.perform(post("/user/register").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user))).andExpect(status().isCreated());
    }

    @Test
    public void testCreateUserValidationError() throws Exception {
        Users user = Users.builder().password("testPassword").emailId("testEmail@test.com").build();


        mockMvc.perform(post("/user/register").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user))).andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<Users> users = List.of(Users.builder().username("testUser1").emailId("user1@example.com").build(), Users.builder().username("testUser2").emailId("user2@example.com").build());

        Mockito.when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/user").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2)) // Expect 2 users
                .andExpect(jsonPath("$[0].username").value("testUser1")).andExpect(jsonPath("$[1].username").value("testUser2"));
    }

    @Test
    public void testGetUserById() throws Exception {
        Users user = Users.builder().id(1L).username("testUser").emailId("test@example.com").build();

        Mockito.when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/user/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.username").value("testUser")).andExpect(jsonPath("$.emailId").value("test@example.com"));
    }

    @Test
    public void testGetUserByInvalidId() throws Exception {
        Mockito.when(userService.getUserById(1L)).thenThrow(new ResourceNotFoundException("User not found"));

        mockMvc.perform(get("/user/1")).andExpect(status().isNotFound()).andExpect(jsonPath("$.message").value("User not found"));
    }

    @Test
    public void testDeleteUserById() throws Exception {
        Mockito.doNothing().when(userService).deleteUser(Mockito.anyLong());

        mockMvc.perform(delete("/user/1")).andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteUserInvalidId() throws Exception {
        Mockito.doThrow(new ResourceNotFoundException("User not found")).when(userService).deleteUser(1L);

        mockMvc.perform(delete("/user/1")).andExpect(status().isNotFound()).andExpect(jsonPath("$.message").value("User not found"));
    }
}