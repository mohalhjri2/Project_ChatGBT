package com.example.project_chatgbt.services;

import com.example.project_chatgbt.entities.Pet;
import com.example.project_chatgbt.dtos.PetSummaryDto;
import com.example.project_chatgbt.dtos.PetStatisticsDto;

import java.util.List;

public interface PetService {
    Pet addPet(Pet pet);
    List<Pet> fetchAllPets();
    Pet fetchPetById(Long id);
    Pet updatePet(Long id, Pet updatedPet);
    void deletePetById(Long id);

    // New methods with consistent return types
    List<Pet> retrievePetsByAnimalType(String animalType);
    List<Pet> retrievePetsByBreedOrderedByAge(String breed);
    List<PetSummaryDto> retrievePetFields(); // Must return List<PetSummaryDto>
    PetStatisticsDto calculateStatistics(); // Must return PetStatisticsDto
}
