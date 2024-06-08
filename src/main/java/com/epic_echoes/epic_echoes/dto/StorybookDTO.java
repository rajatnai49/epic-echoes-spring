package com.epic_echoes.epic_echoes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StorybookDTO {
    private UUID id;
    private String name;
    private List<GenreDTO> genres;
    private String plot;
    private Long maxChapterLength;
    private Long minChapterLength;
    private UUID userId;
    private String privacy;
    private List<ChapterDTO> chapters;
}
