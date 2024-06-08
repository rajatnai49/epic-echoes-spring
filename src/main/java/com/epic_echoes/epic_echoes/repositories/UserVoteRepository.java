package com.epic_echoes.epic_echoes.repositories;

import com.epic_echoes.epic_echoes.entities.Chapter;
import com.epic_echoes.epic_echoes.entities.UserInfo;
import com.epic_echoes.epic_echoes.entities.UserVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserVoteRepository extends JpaRepository<UserVote, UUID> {
    Optional<UserVote> findByUserAndChapter(UserInfo user, Chapter chapter);
}
