package com.epic_echoes.epic_echoes.controllers;

import com.epic_echoes.epic_echoes.dto.ChapterDTO;
import com.epic_echoes.epic_echoes.dto.VoteRequest;
import com.epic_echoes.epic_echoes.services.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/chapters")
public class ChapterController {

    private final ChapterService chapterService;

    @Autowired
    public ChapterController(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    @GetMapping
    public ResponseEntity<List<ChapterDTO>> getAllChapters() {
        List<ChapterDTO> chapters = chapterService.getAllChapters();
        return new ResponseEntity<>(chapters, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChapterDTO> getChapterById(@PathVariable UUID id) {
        ChapterDTO chapterDTO = chapterService.getChapterById(id);
        return new ResponseEntity<>(chapterDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ChapterDTO> createChapter(@RequestBody ChapterDTO chapterDTO) {
        ChapterDTO createdChapter = chapterService.createChapter(chapterDTO);
        return new ResponseEntity<>(createdChapter, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChapterDTO> updateChapter(@PathVariable UUID id, @RequestBody ChapterDTO chapterDTO) {
        ChapterDTO updatedChapter = chapterService.updateChapter(id, chapterDTO);
        return new ResponseEntity<>(updatedChapter, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChapter(@PathVariable UUID id) {
        chapterService.deleteChapter(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/upvote")
    public ResponseEntity<ChapterDTO> upvoteChapter(@RequestBody VoteRequest voteRequest) {
        ChapterDTO updatedChapter = chapterService.upvoteChapter(voteRequest);
        return new ResponseEntity<>(updatedChapter, HttpStatus.OK);
    }

    @PostMapping("/downvote")
    public ResponseEntity<ChapterDTO> downvoteChapter(@RequestBody VoteRequest voteRequest) {
        ChapterDTO updatedChapter = chapterService.downvoteChapter(voteRequest);
        return new ResponseEntity<>(updatedChapter, HttpStatus.OK);
    }
}
