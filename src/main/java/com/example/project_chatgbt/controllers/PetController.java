package com.example.project_chatgbt.controllers;

import com.example.project_chatgbt.dtos.PetDto;
import com.example.project_chatgbt.entities.Pet;
import com.example.project_chatgbt.services.PetService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public ResponseEntity<List<Pet>> getAllPets() {
        return ResponseEntity.ok(petService.fetchAllPets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable Long id) {
        return ResponseEntity.ok(petService.fetchPetById(id));
    }

    @PostMapping
    public ResponseEntity<Pet> createPet(@Valid @RequestBody PetDto petDto) {
        Pet pet = new Pet(null, petDto.name(), petDto.animalType(), petDto.breed(), petDto.age(), null);
        return ResponseEntity.ok(petService.addPet(pet));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pet> updatePet(@PathVariable Long id, @Valid @RequestBody PetDto petDto) {
        Pet pet = petService.fetchPetById(id);
        pet.setName(petDto.name());
        pet.setAnimalType(petDto.animalType());
        pet.setBreed(petDto.breed());
        pet.setAge(petDto.age());
        return ResponseEntity.ok(petService.updatePet(id, pet));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        petService.deletePetById(id);
        return ResponseEntity.noContent().build();
    }
}
