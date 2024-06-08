package com.epic_echoes.epic_echoes.controllers;

import com.epic_echoes.epic_echoes.dto.ChapterDTO;
import com.epic_echoes.epic_echoes.dto.VoteRequest;
import com.epic_echoes.epic_echoes.services.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @Autowired
    private ChapterService chapterService;

    @MessageMapping("/upvote")
    @SendTo("/topic/updates")
    public ChapterDTO upvote(VoteRequest voteRequest) {
        return chapterService.upvoteChapter(voteRequest);
    }

    @MessageMapping("/downvote")
    @SendTo("/topic/updates")
    public ChapterDTO downvote(VoteRequest voteRequest) {
        return chapterService.downvoteChapter(voteRequest);
    }

}
