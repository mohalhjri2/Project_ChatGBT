package com.example.project_chatgbt.controllers;

import com.example.project_chatgbt.dtos.HouseholdDto;
import com.example.project_chatgbt.entities.Household;
import com.example.project_chatgbt.services.HouseholdService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/households")
public class HouseholdController {

    private final HouseholdService householdService;

    public HouseholdController(HouseholdService householdService) {
        this.householdService = householdService;
    }

    @GetMapping
    public ResponseEntity<List<Household>> getAllHouseholds() {
        return ResponseEntity.ok(householdService.retrieveAllHouseholds());
    }

    @GetMapping("/no-pets")
    public ResponseEntity<List<Household>> getHouseholdsWithNoPets() {
        return ResponseEntity.ok(householdService.findHouseholdsWithNoPets());
    }

    @GetMapping("/{eircode}")
    public ResponseEntity<Household> getHouseholdByEircode(@PathVariable String eircode) {
        return ResponseEntity.ok(householdService.retrieveHouseholdByEircode(eircode, true));
    }

    @PostMapping
    public ResponseEntity<Household> createHousehold(@Valid @RequestBody HouseholdDto householdDto) {
        Household household = new Household(
                householdDto.eircode(),
                householdDto.numberOfOccupants(),
                householdDto.maxNumberOfOccupants(),
                householdDto.ownerOccupied(),
                null
        );
        return ResponseEntity.ok(householdService.createHousehold(household));
    }

    @PutMapping("/{eircode}")
    public ResponseEntity<Household> updateHousehold(
            @PathVariable String eircode,
            @Valid @RequestBody HouseholdDto householdDto
    ) {
        Household household = householdService.retrieveHouseholdByEircode(eircode, false);
        household.setNumberOfOccupants(householdDto.numberOfOccupants());
        household.setMaxNumberOfOccupants(householdDto.maxNumberOfOccupants());
        household.setOwnerOccupied(householdDto.ownerOccupied());
        return ResponseEntity.ok(householdService.updateHousehold(eircode, household));
    }

    @DeleteMapping("/{eircode}")
    public ResponseEntity<Void> deleteHousehold(@PathVariable String eircode) {
        householdService.deleteHousehold(eircode);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getHouseholdStatistics() {
        return ResponseEntity.ok(householdService.getHouseholdStatistics());
    }
}
