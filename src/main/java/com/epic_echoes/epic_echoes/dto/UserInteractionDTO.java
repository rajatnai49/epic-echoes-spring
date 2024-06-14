package com.epic_echoes.epic_echoes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInteractionDTO {

    private UUID id;
    private UUID userId;
    private UUID storybookId;
    private LocalDateTime interactionTime;
    private String interactionType;

}
