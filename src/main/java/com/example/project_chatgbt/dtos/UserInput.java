package com.example.project_chatgbt.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserInput(
        @NotBlank @Email String username,
        @NotBlank @Size(min = 8, message = "Password must be at least 8 characters") String password,
        @NotBlank String firstName,
        @NotBlank String lastName,
        String county,
        @NotBlank String role,
        Boolean locked
) {}
