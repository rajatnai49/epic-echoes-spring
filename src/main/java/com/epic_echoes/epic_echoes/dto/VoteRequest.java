package com.epic_echoes.epic_echoes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteRequest {
    private UUID userId;
    private UUID chapterId;
}
