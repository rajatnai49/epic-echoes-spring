package com.epic_echoes.epic_echoes.repositories;

import com.epic_echoes.epic_echoes.entities.StorybookRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StorybookRatingRepository extends JpaRepository<StorybookRating, UUID> {
    List<StorybookRating> findAllByStorybookId(UUID storybookId);
    List<StorybookRating> findAllByUserId(UUID userId);
    StorybookRating findByStorybookIdAndUserId(UUID storybookId, UUID userId);

}
