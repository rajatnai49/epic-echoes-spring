package com.epic_echoes.epic_echoes.controllers;

import com.epic_echoes.epic_echoes.dto.StorybookRatingDTO;
import com.epic_echoes.epic_echoes.services.StorybookRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/storybook-ratings")
public class StorybookRatingController {

    private final StorybookRatingService storybookRatingService;

    @Autowired
    public StorybookRatingController(StorybookRatingService storybookRatingService) {
        this.storybookRatingService = storybookRatingService;
    }

    @GetMapping("/storybook/{storybookId}")
    public ResponseEntity<List<StorybookRatingDTO>> getRatingsByStorybook(@PathVariable UUID storybookId) {
        List<StorybookRatingDTO> ratings = storybookRatingService.getRatingsByStorybook(storybookId);
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<StorybookRatingDTO>> getRatingsByUser(@PathVariable UUID userId) {
        List<StorybookRatingDTO> ratings = storybookRatingService.getRatingsByUser(userId);
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/storybook/{storybookId}/user/{userId}")
    public ResponseEntity<StorybookRatingDTO> getRatingByStorybookAndUser(@PathVariable UUID storybookId, @PathVariable UUID userId) {
        StorybookRatingDTO rating = storybookRatingService.getRatingByStorybookAndUser(storybookId, userId);
        return ResponseEntity.ok(rating);
    }

    @PostMapping
    public ResponseEntity<StorybookRatingDTO> createRating(@RequestBody StorybookRatingDTO ratingDTO) {
        StorybookRatingDTO createdRating = storybookRatingService.createRating(ratingDTO);
        return new ResponseEntity<>(createdRating, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StorybookRatingDTO> updateRating(@PathVariable UUID id, @RequestBody StorybookRatingDTO ratingDTO) {
        StorybookRatingDTO updatedRating = storybookRatingService.updateRating(id, ratingDTO);
        return ResponseEntity.ok(updatedRating);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable UUID id) {
        storybookRatingService.deleteRating(id);
        return ResponseEntity.noContent().build();
    }
}
