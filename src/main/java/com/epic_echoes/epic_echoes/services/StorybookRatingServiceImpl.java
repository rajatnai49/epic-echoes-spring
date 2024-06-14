package com.epic_echoes.epic_echoes.services;

import com.epic_echoes.epic_echoes.dto.StorybookRatingDTO;
import com.epic_echoes.epic_echoes.entities.StorybookRating;
import com.epic_echoes.epic_echoes.repositories.StorybookRatingRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class StorybookRatingServiceImpl implements StorybookRatingService {

    private final StorybookRatingRepository storybookRatingRepository;
    private final ModelMapper modelMapper;

    public StorybookRatingServiceImpl(StorybookRatingRepository storybookRatingRepository, ModelMapper modelMapper) {
        this.storybookRatingRepository = storybookRatingRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<StorybookRatingDTO> getRatingsByStorybook(UUID storybookId) {
        List<StorybookRating> ratings = storybookRatingRepository.findAllByStorybookId(storybookId);

        if (ratings.isEmpty()) {
            throw new RuntimeException("Ratings Not Found");
        }

        return ratings.stream()
                .map(rating -> modelMapper.map(rating, StorybookRatingDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public List<StorybookRatingDTO> getRatingsByUser(UUID userId) {
        List<StorybookRating> ratings = storybookRatingRepository.findAllByUserId(userId);
        if(ratings.isEmpty()) {
            throw new RuntimeException("Ratings Not Found");
        }
        return ratings.stream()
                .map(rating -> modelMapper.map(rating, StorybookRatingDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public StorybookRatingDTO getRatingByStorybookAndUser(UUID storybookId, UUID userId) {
        StorybookRating rating = storybookRatingRepository.findByStorybookIdAndUserId(storybookId, userId);
        if(rating == null) {
            throw new RuntimeException("Rating Not Found");
        }
        return modelMapper.map(rating, StorybookRatingDTO.class);
    }

    @Override
    @Transactional
    public StorybookRatingDTO createRating(StorybookRatingDTO ratingDTO) {
        StorybookRating rating = modelMapper.map(ratingDTO, StorybookRating.class);
        StorybookRating savedRating = storybookRatingRepository.save(rating);
        return modelMapper.map(savedRating, StorybookRatingDTO.class);
    }

    @Override
    @Transactional
    public StorybookRatingDTO updateRating(UUID id, StorybookRatingDTO ratingDTO) {
        Optional<StorybookRating> optionalRating = storybookRatingRepository.findById(id);
        if (optionalRating.isPresent()) {
            StorybookRating existingRating = optionalRating.get();
            existingRating.setRating(ratingDTO.getRating());
            StorybookRating updatedRating = storybookRatingRepository.save(existingRating);
            return modelMapper.map(updatedRating, StorybookRatingDTO.class);
        } else {
            throw new RuntimeException("Rating not found with id: " + id);
        }

    }

    @Override
    public boolean deleteRating(UUID id) {
        Optional<StorybookRating> optionalRating = storybookRatingRepository.findById(id);
        if (optionalRating.isPresent()) {
            storybookRatingRepository.delete(optionalRating.get());
            return true;
        } else {
            throw new RuntimeException("Rating not found with id: " + id);
        }
    }
}
