package com.epic_echoes.epic_echoes.controllers;

import com.epic_echoes.epic_echoes.dto.UserPreferenceDTO;
import com.epic_echoes.epic_echoes.services.UserPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user-preferences")
public class UserPreferenceController {

    private final UserPreferenceService userPreferenceService;

    @Autowired
    public UserPreferenceController(UserPreferenceService userPreferenceService) {
        this.userPreferenceService = userPreferenceService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<UserPreferenceDTO>> getAllUserPreferences(@PathVariable UUID userId) {
        List<UserPreferenceDTO> userPreferences = userPreferenceService.getAllUserPreferences(userId);
        return ResponseEntity.ok(userPreferences);
    }

    @PostMapping
    public ResponseEntity<UserPreferenceDTO> createUserPreference(@RequestBody UserPreferenceDTO userPreferenceDTO) {
        UserPreferenceDTO createdUserPreference = userPreferenceService.createUserPreference(userPreferenceDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserPreference);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserPreferenceDTO> updateUserPreference(@PathVariable UUID id,
                                                                  @RequestBody UserPreferenceDTO userPreferenceDTO) {
        UserPreferenceDTO updatedUserPreference = userPreferenceService.updateUserPreference(id, userPreferenceDTO);
        return ResponseEntity.ok(updatedUserPreference);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserPreference(@PathVariable UUID id) {
        userPreferenceService.deleteUserPreference(id);
        return ResponseEntity.noContent().build();
    }
}
