package com.example.services.impl;

import com.example.model.Users;
import com.example.repository.UserRepository;
import com.example.services.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    public UserServiceImpl(UserRepository repository){
        this.repository = repository;
    }

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    @Override
    public Users createUser(Users user){
        user.setPassword(encoder.encode(user.getPassword()));
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
