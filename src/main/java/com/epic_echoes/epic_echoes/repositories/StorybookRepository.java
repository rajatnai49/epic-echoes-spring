package com.epic_echoes.epic_echoes.repositories;

import com.epic_echoes.epic_echoes.entities.Storybook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StorybookRepository extends JpaRepository<Storybook, UUID> {
    List<Storybook> findByUserId(UUID userId);

    @Query("SELECT s FROM Storybook s JOIN s.genres g WHERE g.id IN :genreIds")
    List<Storybook> findByGenres(@Param("genreIds") List<UUID> genreIds);

    @Query("SELECT s FROM Storybook s WHERE s.privacy = :privacy OR s.privacy = 'EVERYONE_VIEW' OR s.privacy = 'EVERYONE_EDIT' OR s.id IN (SELECT p.storybook.id FROM StorybookUserPermission p WHERE p.user.id = :userId)")
    List<Storybook> findStorybooksByUserAndPrivacy(@Param("userId") UUID userId, @Param("privacy") Storybook.Privacy privacy);

    @Query("SELECT s FROM Storybook s WHERE s.privacy = 'EVERYONE_VIEW' OR s.privacy = 'EVERYONE_EDIT' OR s.id IN (SELECT p.storybook.id FROM StorybookUserPermission p WHERE p.user.id = :userId)")
    List<Storybook> findStorybooksByUser(@Param("userId") UUID userId);
}
