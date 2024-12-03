package com.example.project_chatgbt;

import com.example.project_chatgbt.entities.Household;
import com.example.project_chatgbt.entities.Pet;
import com.example.project_chatgbt.exceptions.BadDataException;
import com.example.project_chatgbt.exceptions.NotFoundException;
import com.example.project_chatgbt.repositories.PetRepository;
import com.example.project_chatgbt.services.PetService;
import com.example.project_chatgbt.services.PetServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PetServiceTest {

    private PetRepository petRepository;
    private PetService petService;

    private Pet pet;
    private Household household;

    @BeforeEach
    void setUp() {
        petRepository = mock(PetRepository.class);
        petService = new PetServiceImpl(petRepository);

        // Create a mock Household
        household = new Household("D12ABC1", 3, 5, true, null);

        // Create a mock Pet
        pet = new Pet(1L, "Buddy", "Dog", "Golden Retriever", 5, household);
    }

    @Test
    void testAddPet_ValidData() {
        when(petRepository.save(any(Pet.class))).thenReturn(pet);

        Pet result = petService.addPet(pet);

        assertEquals("Buddy", result.getName());
        assertEquals(household, result.getHousehold());
        verify(petRepository, times(1)).save(pet);
    }

    @Test
    void testAddPet_InvalidData() {
        Pet invalidPet = new Pet(null, null, "Dog", "Golden Retriever", -1, household);

        assertThrows(BadDataException.class, () -> petService.addPet(invalidPet));
        verify(petRepository, never()).save(any());
    }

    @Test
    void testFetchAllPets() {
        List<Pet> pets = Arrays.asList(pet, new Pet(2L, "Whiskers", "Cat", "Siamese", 3, household));
        when(petRepository.findAll()).thenReturn(pets);

        List<Pet> result = petService.fetchAllPets();

        assertEquals(2, result.size());
        assertEquals("Buddy", result.get(0).getName());
        assertEquals("Whiskers", result.get(1).getName());
    }

    @Test
    void testFetchPetById_ValidId() {
        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        Pet result = petService.fetchPetById(1L);

        assertEquals("Buddy", result.getName());
        verify(petRepository, times(1)).findById(1L);
    }

    @Test
    void testFetchPetById_InvalidId() {
        when(petRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> petService.fetchPetById(1L));
        verify(petRepository, times(1)).findById(1L);
    }

    @Test
    void testDeletePetById_ValidId() {
        when(petRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> petService.deletePetById(1L));
        verify(petRepository, times(1)).existsById(1L);
        verify(petRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeletePetById_InvalidId() {
        when(petRepository.existsById(1L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> petService.deletePetById(1L));
        verify(petRepository, times(1)).existsById(1L);
        verify(petRepository, never()).deleteById(any());
    }
}
