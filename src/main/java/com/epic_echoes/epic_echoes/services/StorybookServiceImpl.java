package com.epic_echoes.epic_echoes.services;

import com.epic_echoes.epic_echoes.dto.GenreDTO;
import com.epic_echoes.epic_echoes.dto.StorybookDTO;
import com.epic_echoes.epic_echoes.entities.Genre;
import com.epic_echoes.epic_echoes.entities.Storybook;
import com.epic_echoes.epic_echoes.entities.UserInfo;
import com.epic_echoes.epic_echoes.repositories.StorybookRepository;
import com.epic_echoes.epic_echoes.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StorybookServiceImpl implements StorybookService {

    private final StorybookRepository storybookRepository;
    private final UserRepository userRepository;
    private final GenreService genreService;
    private final ModelMapper modelMapper;

    @Autowired
    public StorybookServiceImpl(StorybookRepository storybookRepository, UserRepository userRepository, GenreService genreService, ModelMapper modelMapper) {
        this.storybookRepository = storybookRepository;
        this.userRepository = userRepository;
        this.genreService = genreService;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StorybookDTO> getAllStorybooks() {
        List<Storybook> storybooks = storybookRepository.findAll();
        return storybooks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public StorybookDTO getStorybookById(UUID id) {
        Storybook storybook = storybookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Storybook not found"));
        return convertToDto(storybook);
    }

    @Override
    @Transactional
    public StorybookDTO createStorybook(StorybookDTO storybookDTO) {
        Storybook storybook = convertToEntity(storybookDTO);
        UserInfo user = userRepository.findById(storybookDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        storybook.setUser(user);
        Storybook savedStorybook = storybookRepository.save(storybook);
        return convertToDto(savedStorybook);
    }

    @Override
    @Transactional
    public StorybookDTO updateStorybook(UUID id, StorybookDTO storybookDTO) {
        Storybook existingStorybook = storybookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Storybook not found"));
        existingStorybook.setName(storybookDTO.getName());
        existingStorybook.setPlot(storybookDTO.getPlot());
        existingStorybook.setMaxChapterLength(storybookDTO.getMaxChapterLength());
        existingStorybook.setMinChapterLength(storybookDTO.getMinChapterLength());

        UserInfo user = userRepository.findById(storybookDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        existingStorybook.setUser(user);

        List<Genre> genres = genreService.findOrCreateGenres(storybookDTO.getGenres());
        existingStorybook.setGenres(genres);

        Storybook updatedStorybook = storybookRepository.save(existingStorybook);
        return convertToDto(updatedStorybook);
    }

    private StorybookDTO convertToDto(Storybook storybook) {
        StorybookDTO storybookDTO = modelMapper.map(storybook, StorybookDTO.class);
        List<GenreDTO> genreDTOs = storybook.getGenres().stream()
                .map(genre -> modelMapper.map(genre, GenreDTO.class))
                .collect(Collectors.toList());
        storybookDTO.setGenres(genreDTOs);
        return storybookDTO;
    }

    private Storybook convertToEntity(StorybookDTO storybookDTO) {
        Storybook storybook = modelMapper.map(storybookDTO, Storybook.class);
        List<Genre> genres = genreService.findOrCreateGenres(storybookDTO.getGenres());
        storybook.setGenres(genres);
        return storybook;
    }
}
