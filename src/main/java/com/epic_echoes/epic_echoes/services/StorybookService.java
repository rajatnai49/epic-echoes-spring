package com.epic_echoes.epic_echoes.services;

import com.epic_echoes.epic_echoes.dto.ChapterDTO;
import com.epic_echoes.epic_echoes.dto.StorybookDTO;

import java.util.List;
import java.util.UUID;

public interface StorybookService {
    List<StorybookDTO> getAllStorybooks();
    List<ChapterDTO> getChaptersByStorybookId(UUID id);
    List<StorybookDTO> getStorybooksByUserId(UUID id);
    List<StorybookDTO> getStorybooksByGenres(List<UUID> genreIds);
    StorybookDTO getStorybookById(UUID id);
    StorybookDTO updatePrivacyById(UUID id, String privacy);
    StorybookDTO createStorybook(StorybookDTO storybookDTO);
    StorybookDTO updateStorybook(UUID id, StorybookDTO storybookDTO);
    boolean deleteStorybookById(UUID id);
}
