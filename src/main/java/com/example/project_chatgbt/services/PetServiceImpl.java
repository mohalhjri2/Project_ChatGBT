package com.example.project_chatgbt.services;

import com.example.project_chatgbt.entities.Pet;
import com.example.project_chatgbt.dtos.PetSummaryDto;
import com.example.project_chatgbt.dtos.PetStatisticsDto;
import com.example.project_chatgbt.exceptions.NotFoundException;
import com.example.project_chatgbt.exceptions.BadDataException;
import com.example.project_chatgbt.repositories.PetRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public Pet addPet(Pet pet) {
        if (pet.getName() == null || pet.getAnimalType() == null || pet.getBreed() == null || pet.getAge() < 0) {
            throw new BadDataException("Invalid data provided for the pet.");
        }
        return petRepository.save(pet);
    }

    @Override
    public List<Pet> fetchAllPets() {
        return petRepository.findAll();
    }

    @Override
    public Pet fetchPetById(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pet with ID " + id + " not found."));
    }

    @Override
    public Pet updatePet(Long id, Pet updatedPet) {
        Pet existingPet = fetchPetById(id);

        if (updatedPet.getName() == null || updatedPet.getAnimalType() == null || updatedPet.getBreed() == null || updatedPet.getAge() < 0) {
            throw new BadDataException("Invalid data provided for updating the pet.");
        }

        existingPet.setName(updatedPet.getName());
        existingPet.setAnimalType(updatedPet.getAnimalType());
        existingPet.setBreed(updatedPet.getBreed());
        existingPet.setAge(updatedPet.getAge());

        return petRepository.save(existingPet);
    }

    @Override
    public void deletePetById(Long id) {
        if (!petRepository.existsById(id)) {
            throw new NotFoundException("Pet with ID " + id + " not found.");
        }
        petRepository.deleteById(id);
    }

    @Override
    public List<Pet> retrievePetsByAnimalType(String animalType) {
        return petRepository.findAll()
                .stream()
                .filter(pet -> pet.getAnimalType().equalsIgnoreCase(animalType))
                .collect(Collectors.toList());
    }

    @Override
    public List<Pet> retrievePetsByBreedOrderedByAge(String breed) {
        return petRepository.findAll()
                .stream()
                .filter(pet -> pet.getBreed().equalsIgnoreCase(breed))
                .sorted(Comparator.comparingInt(Pet::getAge))
                .collect(Collectors.toList());
    }

    @Override
    public List<PetSummaryDto> retrievePetFields() {
        return petRepository.findAll()
                .stream()
                .map(pet -> new PetSummaryDto(pet.getName(), pet.getAnimalType(), pet.getBreed()))
                .collect(Collectors.toList());
    }

    @Override
    public PetStatisticsDto calculateStatistics() {
        List<Pet> pets = petRepository.findAll();

        if (pets.isEmpty()) {
            throw new NotFoundException("No pets available to calculate statistics.");
        }

        double averageAge = pets.stream()
                .mapToInt(Pet::getAge)
                .average()
                .orElse(0);

        int oldestAge = pets.stream()
                .mapToInt(Pet::getAge)
                .max()
                .orElse(0);

        return new PetStatisticsDto(averageAge, oldestAge);
    }
}