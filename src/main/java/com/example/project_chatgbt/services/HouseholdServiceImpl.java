package com.example.project_chatgbt.services;

import com.example.project_chatgbt.entities.Household;
import com.example.project_chatgbt.exceptions.NotFoundException;
import com.example.project_chatgbt.repositories.HouseholdRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HouseholdServiceImpl implements HouseholdService {

    private final HouseholdRepository householdRepository;

    public HouseholdServiceImpl(HouseholdRepository householdRepository) {
        this.householdRepository = householdRepository;
    }

    @Override
    public Household createHousehold(Household household) {
        return householdRepository.save(household);
    }

    @Override
    public List<Household> retrieveAllHouseholds() {
        return householdRepository.findAll();
    }

    @Override
    public Household retrieveHouseholdByEircode(String eircode, boolean includePets) {
        Household household = householdRepository.findById(eircode)
                .orElseThrow(() -> new NotFoundException("Household with Eircode " + eircode + " not found."));

        if (!includePets) {
            household.setPets(null); // Remove pets from the result
        }
        return household;
    }

    @Override
    public Household updateHousehold(String eircode, Household updatedHousehold) {
        Household existingHousehold = householdRepository.findById(eircode)
                .orElseThrow(() -> new NotFoundException("Household with Eircode " + eircode + " not found."));

        existingHousehold.setNumberOfOccupants(updatedHousehold.getNumberOfOccupants());
        existingHousehold.setMaxNumberOfOccupants(updatedHousehold.getMaxNumberOfOccupants());
        existingHousehold.setOwnerOccupied(updatedHousehold.isOwnerOccupied());

        return householdRepository.save(existingHousehold);
    }

    @Override
    public void deleteHousehold(String eircode) {
        Household household = householdRepository.findById(eircode)
                .orElseThrow(() -> new NotFoundException("Household with Eircode " + eircode + " not found."));
        householdRepository.delete(household);
    }

    @Override
    public List<Household> findHouseholdsWithNoPets() {
        return householdRepository.findAll()
                .stream()
                .filter(household -> household.getPets() == null || household.getPets().isEmpty())
                .collect(Collectors.toList());
    }

    @Override
    public List<Household> findOwnerOccupiedHouseholds() {
        return householdRepository.findAll()
                .stream()
                .filter(Household::isOwnerOccupied)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> getHouseholdStatistics() {
        List<Household> households = householdRepository.findAll();

        if (households.isEmpty()) {
            throw new NotFoundException("No households available to calculate statistics.");
        }

        double averageOccupants = households.stream()
                .mapToInt(Household::getNumberOfOccupants)
                .average()
                .orElse(0);

        int maxOccupants = households.stream()
                .mapToInt(Household::getMaxNumberOfOccupants)
                .max()
                .orElse(0);

        long ownerOccupiedCount = households.stream()
                .filter(Household::isOwnerOccupied)
                .count();

        return Map.of(
                "averageOccupants", averageOccupants,
                "maxOccupants", maxOccupants,
                "ownerOccupiedCount", ownerOccupiedCount
        );
    }
}
