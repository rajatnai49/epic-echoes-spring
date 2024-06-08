package com.epic_echoes.epic_echoes.repositories;

import com.epic_echoes.epic_echoes.entities.Storybook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StorybookRepository extends JpaRepository<Storybook, UUID> {
    List<Storybook> findByUserId(UUID userId);
}
