package com.epic_echoes.epic_echoes.controllers;

import com.epic_echoes.epic_echoes.dto.ChapterDTO;
import com.epic_echoes.epic_echoes.dto.StorybookDTO;
import com.epic_echoes.epic_echoes.entities.Genre;
import com.epic_echoes.epic_echoes.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/storybooks")
public class StorybookController {

    private final StorybookService storybookService;
    private final GenreService genreService;
    private final StorybookUserPermissionService permissionService;

    @Autowired
    public StorybookController(StorybookService storybookService, GenreService genreService, StorybookUserPermissionService permissionService) {
        this.storybookService = storybookService;
        this.genreService = genreService;
        this.permissionService = permissionService;
    }

    @GetMapping
    public ResponseEntity<List<StorybookDTO>> getAllStorybooks() {
        List<StorybookDTO> storybooks = storybookService.getAllStorybooks();
        return ResponseEntity.ok(storybooks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StorybookDTO> getStorybookById(@PathVariable UUID id) {
        StorybookDTO storybook = storybookService.getStorybookById(id);
        return ResponseEntity.ok(storybook);
    }

    @GetMapping("/GetChaptersByStorybookId/{id}")
    public ResponseEntity<List<ChapterDTO>> getChaptersByStorybookId(@PathVariable UUID id) {
        List<ChapterDTO> chapters = storybookService.getChaptersByStorybookId(id);
        return ResponseEntity.ok(chapters);
    }

    @GetMapping("/GetStorybooksByUserId/{id}")
    public ResponseEntity<List<StorybookDTO>> getStorybooksByUserId(@PathVariable UUID id) {
        List<StorybookDTO> storybooks = storybookService.getStorybooksByUserId(id);
        return ResponseEntity.ok(storybooks);
    }

    @GetMapping("/filterByPrivacy")
    public ResponseEntity<List<StorybookDTO>> filterStorybooksBasedOnPrivacy(
            @RequestParam UUID userId,
            @RequestParam(required = false) String privacy) {
        List<StorybookDTO> storybooks = permissionService.filterStorybooksBasedOnPrivacy(userId, privacy);
        return ResponseEntity.ok(storybooks);
    }

    @PostMapping("/GetStorybooksByGenres")
    public ResponseEntity<List<StorybookDTO>> getStorybooksByGenres(@RequestBody List<String> genreNames) {
        List<Genre> genres = genreService.findGenresByName(genreNames);
        List<UUID> genreIds = genres.stream().map(Genre::getId).collect(Collectors.toList());
        List<StorybookDTO> storybooks = storybookService.getStorybooksByGenres(genreIds);
        return ResponseEntity.ok(storybooks);
    }

    @PostMapping
    public ResponseEntity<StorybookDTO> createStorybook(@RequestBody StorybookDTO storybookDTO) {
        System.out.println("Story book request called" + storybookDTO);
        StorybookDTO createdStorybook = storybookService.createStorybook(storybookDTO);
        return ResponseEntity.ok(createdStorybook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StorybookDTO> updateStorybook(@PathVariable UUID id, @RequestBody StorybookDTO storybookDTO) {
        StorybookDTO updatedStorybook = storybookService.updateStorybook(id, storybookDTO);
        return ResponseEntity.ok(updatedStorybook);
    }

    @PatchMapping("/{id}/privacy")
    public ResponseEntity<StorybookDTO> updatePrivacyById(@PathVariable UUID id, @RequestParam String privacy) {
        StorybookDTO updatedStorybook = storybookService.updatePrivacyById(id, privacy);
        return ResponseEntity.ok(updatedStorybook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StorybookDTO> deleteStorybookById(@PathVariable UUID id) {
        boolean isDeleted = storybookService.deleteStorybookById(id);
        if(isDeleted) {
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
