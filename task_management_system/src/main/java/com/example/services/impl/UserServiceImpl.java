package com.example.services.impl;

import com.example.model.Users;
import com.example.repository.UserRepository;
import com.example.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    public UserServiceImpl(UserRepository repository){
        this.repository = repository;
    }

    @Override
    public Users createUser(Users user){
        return repository.save(user);
    }

    @Override
    public Users getUserById(Long id){
        return repository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found")
        );
    }

    @Override
    public List<Users> getAllUsers(){
        return repository.findAll();
    }

    @Override
    public void deleteUser(Long id){
        repository.delete(getUserById(id));
    }
}
