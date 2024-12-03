package com.example.project_chatgbt.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Household {

    @Id
    @Size(min = 7, max = 20, message = "Eircode must be between 7 and 20 characters")
    private String eircode;

    @Min(value = 1, message = "Number of occupants must be at least 1")
    private int numberOfOccupants;

    @Max(value = 10, message = "Max number of occupants cannot exceed 10")
    private int maxNumberOfOccupants;

    @NotNull(message = "Owner-occupied must not be null")
    private boolean ownerOccupied;

    @OneToMany(mappedBy = "household", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pet> pets;
}

