package com.example.project_chatgbt.controllers;

import com.example.project_chatgbt.dtos.HouseholdInput;
import com.example.project_chatgbt.entities.Household;
import com.example.project_chatgbt.entities.Pet;
import com.example.project_chatgbt.services.HouseholdService;
import com.example.project_chatgbt.services.PetService;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
public class GraphQLController {

    private final PetService petService;
    private final HouseholdService householdService;

    public GraphQLController(PetService petService, HouseholdService householdService) {
        this.petService = petService;
        this.householdService = householdService;
    }

    @QueryMapping
    public List<Household> allHouseholds() {
        return householdService.retrieveAllHouseholds();
    }

    @QueryMapping
    public List<Pet> petsByAnimalType(String animalType) {
        return petService.retrievePetsByAnimalType(animalType);
    }

    @QueryMapping
    public Household householdByEircode(String eircode) {
        return householdService.retrieveHouseholdByEircode(eircode, true);
    }

    @QueryMapping
    public Pet petById(Long id) {
        return petService.fetchPetById(id);
    }

    @QueryMapping
    public Map<String, Object> householdStatistics() {
        return householdService.getHouseholdStatistics();
    }

    @MutationMapping
    @Secured("ROLE_USER") // Allow authenticated users with USER role
    public Household createHousehold(HouseholdInput household) {
        Household newHousehold = new Household(
                household.eircode(),
                household.numberOfOccupants(),
                household.maxNumberOfOccupants(),
                household.ownerOccupied(),
                null
        );
        return householdService.createHousehold(newHousehold);
    }

    @MutationMapping
    @Secured("ROLE_ADMIN") // Allow only ADMIN role
    public String deleteHousehold(String eircode) {
        householdService.deleteHousehold(eircode);
        return "Household with eircode " + eircode + " deleted successfully.";
    }

    @MutationMapping
    @Secured("ROLE_ADMIN") // Allow only ADMIN role
    public String deletePet(Long id) {
        petService.deletePetById(id);
        return "Pet with ID " + id + " deleted successfully.";
    }
}
