package com.example.project_chatgbt.graphql;

import com.example.project_chatgbt.entities.Household;
import com.example.project_chatgbt.entities.Pet;
import com.example.project_chatgbt.services.HouseholdService;
import com.example.project_chatgbt.services.PetService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;

@Component
public class MutationResolver implements GraphQLMutationResolver {

    private final PetService petService;
    private final HouseholdService householdService;

    public MutationResolver(PetService petService, HouseholdService householdService) {
        this.petService = petService;
        this.householdService = householdService;
    }

    public Pet addPet(String name, String animalType, String breed, int age, String householdEircode) {
        Pet pet = new Pet(null, name, animalType, breed, age, null);
        return petService.addPet(pet);
    }

    public Household addHousehold(String eircode, int numberOfOccupants, int maxNumberOfOccupants, boolean ownerOccupied) {
        Household household = new Household(eircode, numberOfOccupants, maxNumberOfOccupants, ownerOccupied, null);
        return householdService.createHousehold(household);
    }
}
