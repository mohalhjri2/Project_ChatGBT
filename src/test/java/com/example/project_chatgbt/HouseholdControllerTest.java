package com.example.project_chatgbt;

import com.example.project_chatgbt.controllers.HouseholdController;
import com.example.project_chatgbt.dtos.HouseholdDto;
import com.example.project_chatgbt.entities.Household;
import com.example.project_chatgbt.services.HouseholdService;
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

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@ContextConfiguration(classes = HouseholdControllerTest.Config.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class HouseholdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HouseholdService householdService;

    @Autowired
    private ObjectMapper objectMapper;

    private Household household;

    @BeforeEach
    void setUp() {
        household = new Household("D12ABC1", 3, 5, true, null);
    }

    @Test
    void getAllHouseholds_ShouldReturnListOfHouseholds() throws Exception {
        List<Household> households = Collections.singletonList(household);
        when(householdService.retrieveAllHouseholds()).thenReturn(households);

        mockMvc.perform(get("/api/households"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].eircode").value("D12ABC1"));
    }

    @Test
    void createHousehold_ShouldReturnCreatedHousehold() throws Exception {
        HouseholdDto householdDto = new HouseholdDto("D12ABC1", 3, 5, true);
        when(householdService.createHousehold(Mockito.any(Household.class))).thenReturn(household);

        mockMvc.perform(post("/api/households")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(householdDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eircode").value("D12ABC1"));
    }

    @Test
    void createHousehold_InvalidData_ShouldReturn400() throws Exception {
        HouseholdDto householdDto = new HouseholdDto("", 0, 15, true);

        mockMvc.perform(post("/api/households")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(householdDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.eircode").value("Eircode is required"))
                .andExpect(jsonPath("$.numberOfOccupants").value("Number of occupants must be at least 1"))
                .andExpect(jsonPath("$.maxNumberOfOccupants").value("Max number of occupants cannot exceed 10"))
                .andExpect(jsonPath("$.eircode").value("Eircode must be between 7 and 20 characters"));
    }


    static class Config {
        @Bean
        public HouseholdService householdService() {
            return mock(HouseholdService.class);
        }

        @Bean
        public HouseholdController householdController(HouseholdService householdService) {
            return new HouseholdController(householdService);
        }

        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper();
        }
    }
}
