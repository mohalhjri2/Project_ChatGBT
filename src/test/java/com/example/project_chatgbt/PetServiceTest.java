package com.example.project_chatgbt;

import com.example.project_chatgbt.entities.Pet;
import com.example.project_chatgbt.dtos.PetSummaryDto;
import com.example.project_chatgbt.dtos.PetStatisticsDto;
import com.example.project_chatgbt.exceptions.BadDataException;
import com.example.project_chatgbt.exceptions.NotFoundException;
import com.example.project_chatgbt.repositories.PetRepository;
import com.example.project_chatgbt.services.PetService;
import com.example.project_chatgbt.services.PetServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PetServiceTest {

    private PetRepository petRepository;
    private PetService petService;

    @BeforeEach
    void setUp() {
        petRepository = mock(PetRepository.class);
        petService = new PetServiceImpl(petRepository);
    }

    @Test
    void testAddPet_ValidData() {
        Pet pet = new Pet(null, "Buddy", "Dog", "Golden Retriever", 5);
        Pet savedPet = new Pet(1L, "Buddy", "Dog", "Golden Retriever", 5);

        when(petRepository.save(pet)).thenReturn(savedPet);

        Pet result = petService.addPet(pet);

        assertEquals(savedPet, result);
        verify(petRepository).save(pet);
    }

    @Test
    void testAddPet_InvalidData() {
        Pet pet = new Pet(null, null, "Dog", "Golden Retriever", -1);

        assertThrows(BadDataException.class, () -> petService.addPet(pet));
        verify(petRepository, never()).save(any());
    }

    @Test
    void testFetchAllPets() {
        List<Pet> pets = Arrays.asList(
                new Pet(1L, "Buddy", "Dog", "Golden Retriever", 5),
                new Pet(2L, "Whiskers", "Cat", "Siamese", 3)
        );

        when(petRepository.findAll()).thenReturn(pets);

        List<Pet> result = petService.fetchAllPets();

        assertEquals(pets, result);
        verify(petRepository).findAll();
    }

    @Test
    void testFetchPetById_ValidId() {
        Pet pet = new Pet(1L, "Buddy", "Dog", "Golden Retriever", 5);

        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        Pet result = petService.fetchPetById(1L);

        assertEquals(pet, result);
        verify(petRepository).findById(1L);
    }

    @Test
    void testFetchPetById_InvalidId() {
        when(petRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> petService.fetchPetById(1L));
        verify(petRepository).findById(1L);
    }

    @Test
    void testUpdatePet_ValidData() {
        Pet existingPet = new Pet(1L, "Buddy", "Dog", "Golden Retriever", 5);
        Pet updatedPet = new Pet(null, "Buddy", "Dog", "Labrador", 6);
        Pet savedPet = new Pet(1L, "Buddy", "Dog", "Labrador", 6);

        when(petRepository.findById(1L)).thenReturn(Optional.of(existingPet));
        when(petRepository.save(existingPet)).thenReturn(savedPet);

        Pet result = petService.updatePet(1L, updatedPet);

        assertEquals(savedPet, result);
        verify(petRepository).findById(1L);
        verify(petRepository).save(existingPet);
    }

    @Test
    void testUpdatePet_InvalidId() {
        Pet updatedPet = new Pet(null, "Buddy", "Dog", "Labrador", 6);

        when(petRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> petService.updatePet(1L, updatedPet));
        verify(petRepository).findById(1L);
        verify(petRepository, never()).save(any());
    }

    @Test
    void testDeletePetById_ValidId() {
        when(petRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> petService.deletePetById(1L));
        verify(petRepository).existsById(1L);
        verify(petRepository).deleteById(1L);
    }

    @Test
    void testDeletePetById_InvalidId() {
        when(petRepository.existsById(1L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> petService.deletePetById(1L));
        verify(petRepository).existsById(1L);
        verify(petRepository, never()).deleteById(any());
    }

    @Test
    void testRetrievePetFields() {
        List<Pet> pets = Arrays.asList(
                new Pet(1L, "Buddy", "Dog", "Golden Retriever", 5),
                new Pet(2L, "Whiskers", "Cat", "Siamese", 3)
        );

        when(petRepository.findAll()).thenReturn(pets);

        List<PetSummaryDto> result = petService.retrievePetFields();

        assertEquals(2, result.size());
        assertEquals("Buddy", result.get(0).name());
        assertEquals("Whiskers", result.get(1).name());
        verify(petRepository).findAll();
    }

    @Test
    void testCalculateStatistics() {
        List<Pet> pets = Arrays.asList(
                new Pet(1L, "Buddy", "Dog", "Golden Retriever", 5),
                new Pet(2L, "Whiskers", "Cat", "Siamese", 3)
        );

        when(petRepository.findAll()).thenReturn(pets);

        PetStatisticsDto result = petService.calculateStatistics();

        assertEquals(4.0, result.averageAge());
        assertEquals(5, result.oldestAge());
        verify(petRepository).findAll();
    }

    @Test
    void testCalculateStatistics_NoPets() {
        when(petRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(NotFoundException.class, () -> petService.calculateStatistics());
        verify(petRepository).findAll();
    }
}
