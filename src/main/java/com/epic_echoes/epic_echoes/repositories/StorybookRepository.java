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
}
