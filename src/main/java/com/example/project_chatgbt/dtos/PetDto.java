package com.example.project_chatgbt.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PetDto(
        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name cannot exceed 100 characters")
        String name,

        @NotBlank(message = "Animal type is required")
        @Size(max = 50, message = "Animal type cannot exceed 50 characters")
        String animalType,

        @Size(max = 100, message = "Breed cannot exceed 100 characters")
        String breed,

        @Min(value = 0, message = "Age must be a positive number")
        int age,

        @NotNull(message = "Household eircode is required")
        String householdEircode
) {}
