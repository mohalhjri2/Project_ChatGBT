package com.example.project_chatgbt.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record HouseholdInput(
        @NotBlank(message = "Eircode is required")
        @Size(min = 7, max = 20, message = "Eircode must be between 7 and 20 characters")
        String eircode,

        @Min(value = 1, message = "Number of occupants must be at least 1")
        int numberOfOccupants,

        @Max(value = 10, message = "Max number of occupants cannot exceed 10")
        int maxNumberOfOccupants,

        @NotNull(message = "Owner-occupied must not be null")
        boolean ownerOccupied
) {}
