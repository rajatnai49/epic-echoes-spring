package com.epic_echoes.epic_echoes.services;

import com.epic_echoes.epic_echoes.dto.UserInteractionDTO;

import java.util.List;
import java.util.UUID;

public interface UserInteractionService {
    List<UserInteractionDTO> getUserInteractionsByUserId(UUID userId);
    List<UserInteractionDTO> getUserInteractionsByStorybookId(UUID storybookId);
    UserInteractionDTO createUserInteraction(UserInteractionDTO userInteractionDTO);
    boolean deleteUserInteraction(UUID id);
}
