package com.example.project_chatgbt.controllers;

import com.example.project_chatgbt.dtos.UserInput;
import com.example.project_chatgbt.entities.User;
import com.example.project_chatgbt.repositories.UserRepository;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @QueryMapping
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @QueryMapping
    public User userByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }

    @MutationMapping
    public User createUser(UserInput userInput) {
        User user = new User(
                userInput.username(),
                passwordEncoder.encode(userInput.password()),
                userInput.role(),
                Optional.ofNullable(userInput.locked()).orElse(false),
                userInput.firstName(),
                userInput.lastName(),
                userInput.county()
        );
        return userRepository.save(user);
    }

    @MutationMapping
    public String deleteUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        userRepository.delete(user);
        return "User with username " + username + " deleted successfully.";
    }

    @MutationMapping
    public User updateUserRole(String username, String role) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        user.setRole(role);
        return userRepository.save(user);
    }
}
