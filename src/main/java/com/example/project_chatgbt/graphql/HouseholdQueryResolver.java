package com.example.project_chatgbt.graphql;

import com.example.project_chatgbt.entities.Household;
import com.example.project_chatgbt.services.HouseholdService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HouseholdQueryResolver implements GraphQLQueryResolver {

    private final HouseholdService householdService;

    public HouseholdQueryResolver(HouseholdService householdService) {
        this.householdService = householdService;
    }

    public List<Household> allHouseholds() {
        return householdService.retrieveAllHouseholds();
    }

    public Household householdByEircode(String eircode) {
        return householdService.retrieveHouseholdByEircode(eircode, true);
    }
}
