package com.epic_echoes.epic_echoes.controllers;

import com.epic_echoes.epic_echoes.dto.UserInteractionDTO;
import com.epic_echoes.epic_echoes.services.UserInteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user-interactions")
public class UserInteractionController {

    private final UserInteractionService userInteractionService;

    @Autowired
    public UserInteractionController(UserInteractionService userInteractionService) {
        this.userInteractionService = userInteractionService;
    }

    @GetMapping("/{userId}")
    public List<UserInteractionDTO> getAllUserInteractions(@PathVariable UUID userId) {
        return userInteractionService.getUserInteractionsByUserId(userId);
    }
}
