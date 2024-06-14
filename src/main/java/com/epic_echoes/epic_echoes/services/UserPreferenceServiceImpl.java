package com.epic_echoes.epic_echoes.services;

import com.epic_echoes.epic_echoes.dto.UserPreferenceDTO;
import com.epic_echoes.epic_echoes.entities.UserPreference;
import com.epic_echoes.epic_echoes.repositories.UserPreferenceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserPreferenceServiceImpl implements UserPreferenceService {

    private final UserPreferenceRepository userPreferenceRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserPreferenceServiceImpl(UserPreferenceRepository userPreferenceRepository, ModelMapper modelMapper) {
        this.userPreferenceRepository = userPreferenceRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UserPreferenceDTO> getAllUserPreferences(UUID userId) {
        List<UserPreference> userPreferences = userPreferenceRepository.findByUserId(userId);
        return userPreferences.stream()
                .map(userPreference -> modelMapper.map(userPreference, UserPreferenceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserPreferenceDTO createUserPreference(UserPreferenceDTO userPreferenceDTO) {
        UserPreference userPreference = modelMapper.map(userPreferenceDTO, UserPreference.class);
        UserPreference savedUserPreference = userPreferenceRepository.save(userPreference);
        return modelMapper.map(savedUserPreference, UserPreferenceDTO.class);
    }

    @Override
    public UserPreferenceDTO updateUserPreference(UUID id, UserPreferenceDTO userPreferenceDTO) {
        UserPreference userPreference = modelMapper.map(userPreferenceDTO, UserPreference.class);
        userPreference.setId(id); // Ensure the ID is set for update
        UserPreference updatedUserPreference = userPreferenceRepository.save(userPreference);
        return modelMapper.map(updatedUserPreference, UserPreferenceDTO.class);
    }

    @Override
    public void deleteUserPreference(UUID id) {
        userPreferenceRepository.deleteById(id);
    }
}
