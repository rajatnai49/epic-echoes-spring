package com.epic_echoes.epic_echoes.services;

import com.epic_echoes.epic_echoes.dto.UserPreferenceDTO;

import java.util.List;
import java.util.UUID;

public interface UserPreferenceService {
    List<UserPreferenceDTO> getAllUserPreferences(UUID userId);
    UserPreferenceDTO createUserPreference(UserPreferenceDTO userPreferenceDTO);
    UserPreferenceDTO updateUserPreference(UUID id, UserPreferenceDTO userPreferenceDTO);
    void deleteUserPreference(UUID id);
}
