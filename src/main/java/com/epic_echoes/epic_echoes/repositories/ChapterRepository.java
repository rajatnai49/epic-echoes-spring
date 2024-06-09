package com.epic_echoes.epic_echoes.repositories;

import com.epic_echoes.epic_echoes.entities.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, UUID> {
    @Query("SELECT c FROM Chapter c WHERE c.storybook.id = :storybookId AND c.chapterNumber = :chapterNumber")
    List<Chapter> findChaptersByStorybookIdAndNumber(@Param("storybookId") UUID storybookId, @Param("chapterNumber") Integer chapterNumber);
}
