package com.example.services;

import com.example.model.Users;

import java.util.List;

public interface UserService {
    Users createUser(Users user);
    Users getUserById(Long id);
    List<Users> getAllUsers();
    void deleteUser(Long id);
}
