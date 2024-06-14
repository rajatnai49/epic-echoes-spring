package com.epic_echoes.epic_echoes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPreferenceDTO {
    private UUID id;
    private UUID userId;
    private UUID genreId;
}
