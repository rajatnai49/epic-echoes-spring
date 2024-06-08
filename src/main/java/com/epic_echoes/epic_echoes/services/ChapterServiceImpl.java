package com.epic_echoes.epic_echoes.services;

import com.epic_echoes.epic_echoes.dto.ChapterDTO;
import com.epic_echoes.epic_echoes.dto.VoteRequest;
import com.epic_echoes.epic_echoes.entities.Chapter;
import com.epic_echoes.epic_echoes.entities.UserInfo;
import com.epic_echoes.epic_echoes.entities.UserVote;
import com.epic_echoes.epic_echoes.repositories.ChapterRepository;
import com.epic_echoes.epic_echoes.repositories.StorybookRepository;
import com.epic_echoes.epic_echoes.repositories.UserRepository;
import com.epic_echoes.epic_echoes.repositories.UserVoteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    ChapterRepository chapterRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StorybookRepository storybookRepository;

    @Autowired
    UserVoteRepository userVoteRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    final ModelMapper modelMapper;

    @Autowired
    public ChapterServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ChapterDTO> getAllChapters() {
        return chapterRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ChapterDTO getChapterById(UUID id) {
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chapter not found"));
        return convertToDto(chapter);
    }

    @Override
    public ChapterDTO createChapter(ChapterDTO chapterDTO) {
        Chapter chapter = convertToEntity(chapterDTO);
        chapter.setStorybook(storybookRepository.findById(chapterDTO.getStorybookId())
                .orElseThrow(() -> new RuntimeException("Storybook not found")));
        Chapter savedChapter = chapterRepository.save(chapter);
        return convertToDto(savedChapter);
    }

    @Override
    public ChapterDTO updateChapter(UUID id, ChapterDTO chapterDTO) {
        Chapter existingChapter = chapterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chapter Not Found"));
        existingChapter.setTitle(chapterDTO.getTitle());
        existingChapter.setChapterContent(chapterDTO.getChapterContent());
        existingChapter.setChapterNumber(chapterDTO.getChapterNumber());
        existingChapter.setStorybook(storybookRepository.findById(chapterDTO.getStorybookId())
                .orElseThrow(() -> new RuntimeException("Storybook not found")));

        Chapter updatedChapter = chapterRepository.save(existingChapter);
        return convertToDto(updatedChapter);
    }

    @Override
    public void deleteChapter(UUID id) {
        Chapter chapter = chapterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chapter not found"));
        chapterRepository.delete(chapter);
    }

    @Override
    public ChapterDTO upvoteChapter(VoteRequest voteRequest) {
        UserInfo user = userRepository.findById(voteRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        Chapter chapter = chapterRepository.findById(voteRequest.getChapterId())
                .orElseThrow(() -> new RuntimeException("Chapter Not Found"));

        UserVote userVote = userVoteRepository.findByUserAndChapter(user, chapter)
                .orElseGet(() -> new UserVote(null, chapter, user, null));

        if (userVote.getVoteType() == UserVote.VoteType.UPVOTE) {
            chapter.setUpVotes(chapter.getUpVotes() - 1);
            userVoteRepository.delete(userVote);
        } else {
            if (userVote.getVoteType() == UserVote.VoteType.DOWNVOTE) {
                chapter.setDownVotes(chapter.getDownVotes() - 1);
            }
            chapter.setUpVotes(chapter.getUpVotes() + 1);
            userVote.setVoteType(UserVote.VoteType.UPVOTE);
            userVoteRepository.save(userVote);
        }

        Chapter updatedChapter = chapterRepository.save(chapter);
        ChapterDTO chapterDTO = convertToDto(updatedChapter);

        messagingTemplate.convertAndSend("/topic/updates", chapterDTO);
        return chapterDTO;
    }

    @Override
    public ChapterDTO downvoteChapter(VoteRequest voteRequest) {
        UserInfo user = userRepository.findById(voteRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        Chapter chapter = chapterRepository.findById(voteRequest.getChapterId())
                .orElseThrow(() -> new RuntimeException("Chapter Not Found"));

        UserVote userVote = userVoteRepository.findByUserAndChapter(user, chapter)
                .orElseGet(() -> new UserVote(null, chapter, user, null));
        if (userVote.getVoteType() == UserVote.VoteType.DOWNVOTE) {
            chapter.setDownVotes(chapter.getDownVotes() - 1);
            userVoteRepository.delete(userVote);
        } else {
            if (userVote.getVoteType() == UserVote.VoteType.UPVOTE) {
                chapter.setUpVotes(chapter.getUpVotes() - 1);
            }
            chapter.setDownVotes(chapter.getDownVotes() + 1);
            userVote.setVoteType(UserVote.VoteType.DOWNVOTE);
            userVoteRepository.save(userVote);
        }

        Chapter updatedChapter = chapterRepository.save(chapter);
        ChapterDTO chapterDTO = convertToDto(updatedChapter);
        messagingTemplate.convertAndSend("/topic/updates", chapterDTO);
        return chapterDTO;
    }

    private ChapterDTO convertToDto(Chapter chapter) {
        ChapterDTO chapterDTO = modelMapper.map(chapter, ChapterDTO.class);
        if (chapterDTO.getUpVotes() == null) {
            chapterDTO.setUpVotes(0L);
        }
        if (chapterDTO.getDownVotes() == null) {
            chapterDTO.setDownVotes(0L);
        }
        return chapterDTO;
    }

    private Chapter convertToEntity(ChapterDTO chapterDTO) {
        Chapter chapter = modelMapper.map(chapterDTO, Chapter.class);
        if (chapter.getUpVotes() == null) {
            chapter.setUpVotes(0L);
        }
        if (chapter.getDownVotes() == null) {
            chapter.setDownVotes(0L);
        }
        return chapter;
    }
}
