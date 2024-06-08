package com.epic_echoes.epic_echoes.services;

import com.epic_echoes.epic_echoes.dto.ChapterDTO;
import com.epic_echoes.epic_echoes.dto.VoteRequest;

import java.util.List;
import java.util.UUID;

public interface ChapterService {
    List<ChapterDTO> getAllChapters();
    ChapterDTO getChapterById(UUID id);
    ChapterDTO createChapter(ChapterDTO chapterDTO);
    ChapterDTO updateChapter(UUID id, ChapterDTO chapterDTO);
    void deleteChapter(UUID id);
    ChapterDTO upvoteChapter(VoteRequest voteRequest);
    ChapterDTO downvoteChapter(VoteRequest voteRequest);
}
