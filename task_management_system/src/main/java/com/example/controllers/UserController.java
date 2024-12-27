package com.example.controllers;

import com.example.model.User;
import com.example.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@RequestParam Long id){
        User requestedUser = userService.getUserById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(requestedUser);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User createdUser = userService.createUser(user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(createdUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@RequestParam Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
