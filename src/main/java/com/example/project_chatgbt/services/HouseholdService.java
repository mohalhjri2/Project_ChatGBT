package com.example.project_chatgbt.services;

import com.example.project_chatgbt.entities.Household;

import java.util.List;
import java.util.Map;

public interface HouseholdService {

    Household createHousehold(Household household);

    List<Household> retrieveAllHouseholds();

    Household retrieveHouseholdByEircode(String eircode, boolean includePets);

    Household updateHousehold(String eircode, Household updatedHousehold);

    void deleteHousehold(String eircode);

    List<Household> findHouseholdsWithNoPets();

    List<Household> findOwnerOccupiedHouseholds();

    Map<String, Object> getHouseholdStatistics();
}
