package com.example.project_chatgbt.repositories;

import com.example.project_chatgbt.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
    // Additional custom query methods can be defined here if needed
}

