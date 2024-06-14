package com.epic_echoes.epic_echoes.services;

import com.epic_echoes.epic_echoes.dto.ChapterDTO;
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
import java.util.Optional;
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
        System.out.println(storybooks);
        return storybooks.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public StorybookDTO getStorybookById(UUID id) {
        Storybook storybook = storybookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Storybook not found"));
        return convertToDTO(storybook);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChapterDTO> getChaptersByStorybookId(UUID id) {
        StorybookDTO storybookDTO = getStorybookById(id);
        return storybookDTO.getChapters();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StorybookDTO> getStorybooksByUserId(UUID id) {
        List<Storybook> storybooks = storybookRepository.findByUserId(id);
        return storybooks.stream().map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<StorybookDTO> getStorybooksByGenres(List<UUID> genreIds) {
        List<Storybook> storybooks = storybookRepository.findByGenres(genreIds);
        return storybooks.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public StorybookDTO createStorybook(StorybookDTO storybookDTO) {
        Storybook storybook = convertToEntity(storybookDTO);
        if (storybook == null) {
            throw new IllegalArgumentException("Converted Storybook entity cannot be null");
        }

        UserInfo user = userRepository.findById(storybookDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        storybook.setUser(user);
        Storybook savedStorybook = storybookRepository.save(storybook);
        if (savedStorybook == null) {
            throw new RuntimeException("Failed to save storybook");
        }
        return convertToDTO(savedStorybook);
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

        if (updatedStorybook == null) {
            throw new RuntimeException("Failed to update storybook");
        }
        return convertToDTO(updatedStorybook);
    }


    @Override
    @Transactional
    public StorybookDTO updatePrivacyById(UUID id, String privacy) {
        Optional<Storybook> optionalStorybook = storybookRepository.findById(id);
        if (optionalStorybook.isPresent()) {
            Storybook storybook = optionalStorybook.get();
            try {
                Storybook.Privacy privacyEnum = Storybook.Privacy.valueOf(privacy);
                storybook.setPrivacy(privacyEnum);
                storybook = storybookRepository.save(storybook);
                return convertToDTO(storybook);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid privacy value: " + privacy);
            }
        } else {
            throw new IllegalArgumentException("Storybook not found with id: " + id);
        }
    }

    @Override
    @Transactional
    public boolean deleteStorybookById(UUID id) {
        if(storybookRepository.existsById(id)) {
            storybookRepository.deleteById(id);
            return true;
        }
        else {
            return false;
        }
    }

    private StorybookDTO convertToDTO(Storybook storybook) {
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
