package com.example.project_chatgbt.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "Animal type is required")
    @Size(max = 50, message = "Animal type cannot exceed 50 characters")
    private String animalType;

    @Size(max = 100, message = "Breed cannot exceed 100 characters")
    private String breed;

    @Min(value = 0, message = "Age must be a positive number")
    private int age;

    @ManyToOne
    @JoinColumn(name = "household_eircode")
    private Household household;
}

