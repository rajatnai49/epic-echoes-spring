package com.epic_echoes.epic_echoes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChapterDTO {
    private UUID id;
    private String title;
    private Long upVotes = 0L;
    private Long downVotes = 0L;
    private Integer chapterNumber;
    private String chapterContent;
    private UUID storybookId;
    private UUID userId;
}
