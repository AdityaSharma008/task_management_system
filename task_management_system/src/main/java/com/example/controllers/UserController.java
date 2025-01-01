package com.example.controllers;

import com.example.dto.UserDTO;
import com.example.model.Users;
import com.example.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers().stream()
                .map(UserDTO::toUserDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@RequestParam Long id){
        Users requestedUser = userService.getUserById(id);
        UserDTO requestedUserDTO = UserDTO.toUserDTO(requestedUser);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(requestedUserDTO);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody @Valid Users user){
        Users createdUser = userService.createUser(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User created successfully!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@RequestParam Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
