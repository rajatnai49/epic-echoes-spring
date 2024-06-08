package com.epic_echoes.epic_echoes.services;

import com.epic_echoes.epic_echoes.dto.StorybookDTO;

import java.util.List;
import java.util.UUID;

public interface StorybookService {
    List<StorybookDTO> getAllStorybooks();
    StorybookDTO getStorybookById(UUID id);
    StorybookDTO createStorybook(StorybookDTO storybookDTO);
    StorybookDTO updateStorybook(UUID id, StorybookDTO storybookDTO);
}
