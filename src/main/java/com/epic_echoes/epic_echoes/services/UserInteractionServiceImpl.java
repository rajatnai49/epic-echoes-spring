package com.epic_echoes.epic_echoes.services;

import com.epic_echoes.epic_echoes.dto.UserInteractionDTO;
import com.epic_echoes.epic_echoes.entities.UserInteraction;
import com.epic_echoes.epic_echoes.repositories.UserInteractionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserInteractionServiceImpl implements UserInteractionService {

    private final UserInteractionRepository userInteractionRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserInteractionServiceImpl(UserInteractionRepository userInteractionRepository, ModelMapper modelMapper) {
        this.userInteractionRepository = userInteractionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserInteractionDTO> getUserInteractionsByUserId(UUID userId) {
        List<UserInteraction> userInteractions = userInteractionRepository.findByUserId(userId);
        return userInteractions.stream()
                .map(interaction -> modelMapper.map(interaction, UserInteractionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserInteractionDTO> getUserInteractionsByStorybookId(UUID storybookId) {
        List<UserInteraction> userInteractions = userInteractionRepository.findByStorybookId(storybookId);
        return userInteractions.stream()
                .map(interaction -> modelMapper.map(interaction, UserInteractionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserInteractionDTO createUserInteraction(UserInteractionDTO userInteractionDTO) {
        UserInteraction userInteraction = modelMapper.map(userInteractionDTO, UserInteraction.class);
        UserInteraction savedInteraction = userInteractionRepository.save(userInteraction);
        return modelMapper.map(savedInteraction, UserInteractionDTO.class);
    }

    @Override
    @Transactional
    public boolean deleteUserInteraction(UUID id) {
        if(userInteractionRepository.existsById(id)) {
            userInteractionRepository.deleteById(id);
            return true;
        }
        else {
            throw new RuntimeException("User Interaction Not Found");
        }
    }
}
