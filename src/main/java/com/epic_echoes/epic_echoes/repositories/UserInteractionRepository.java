package com.epic_echoes.epic_echoes.repositories;

import com.epic_echoes.epic_echoes.entities.UserInteraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserInteractionRepository extends JpaRepository<UserInteraction, UUID> {
    List<UserInteraction> findByUserId(UUID userId);
    List<UserInteraction> findByStorybookId(UUID storybookId);
}
