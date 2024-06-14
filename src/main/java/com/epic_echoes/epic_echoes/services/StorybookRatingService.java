package com.epic_echoes.epic_echoes.services;

import com.epic_echoes.epic_echoes.dto.StorybookRatingDTO;
import com.epic_echoes.epic_echoes.entities.StorybookRating;

import java.util.List;
import java.util.UUID;

public interface StorybookRatingService {
    List<StorybookRatingDTO> getRatingsByStorybook(UUID storybookId);
    List<StorybookRatingDTO> getRatingsByUser(UUID userId);
    StorybookRatingDTO getRatingByStorybookAndUser(UUID storybookId, UUID userId);
    StorybookRatingDTO createRating(StorybookRatingDTO storybookRatingDTO);
    StorybookRatingDTO updateRating(UUID id, StorybookRatingDTO ratingDTO);
    boolean deleteRating(UUID id);
}
