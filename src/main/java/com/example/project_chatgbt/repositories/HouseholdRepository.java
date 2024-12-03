package com.example.project_chatgbt.repositories;

import com.example.project_chatgbt.entities.Household;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseholdRepository extends JpaRepository<Household, String> {
    // Additional query methods can be added here if needed
}
