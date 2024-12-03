package com.example.project_chatgbt;

import com.example.project_chatgbt.controllers.PetController;
import com.example.project_chatgbt.dtos.PetDto;
import com.example.project_chatgbt.entities.Pet;
import com.example.project_chatgbt.services.PetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@ContextConfiguration(classes = PetControllerTest.Config.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PetService petService;

    @Autowired
    private ObjectMapper objectMapper;

    private Pet pet;

    @BeforeEach
    void setUp() {
        pet = new Pet(1L, "Buddy", "Dog", "Golden Retriever", 5, null);
    }

    @Test
    void getAllPets_ShouldReturnListOfPets() throws Exception {
        List<Pet> pets = Arrays.asList(pet, new Pet(2L, "Whiskers", "Cat", "Siamese", 3, null));
        when(petService.fetchAllPets()).thenReturn(pets);

        mockMvc.perform(get("/api/pets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Buddy"));
    }

    @Test
    void createPet_ShouldReturnCreatedPet() throws Exception {
        PetDto petDto = new PetDto("Buddy", "Dog", "Golden Retriever", 5, "D12ABC1");
        when(petService.addPet(Mockito.any(Pet.class))).thenReturn(pet);

        mockMvc.perform(post("/api/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(petDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Buddy"));
    }

    static class Config {
        @Bean
        public PetService petService() {
            return mock(PetService.class);
        }

        @Bean
        public PetController petController(PetService petService) {
            return new PetController(petService);
        }

        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper();
        }
    }
}
