package com.example.project_chatgbt.graphql;

import com.example.project_chatgbt.entities.Pet;
import com.example.project_chatgbt.services.PetService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PetQueryResolver implements GraphQLQueryResolver {

    private final PetService petService;

    public PetQueryResolver(PetService petService) {
        this.petService = petService;
    }

    public List<Pet> allPets() {
        return petService.fetchAllPets();
    }

    public Pet petById(Long id) {
        return petService.fetchPetById(id);
    }
}
