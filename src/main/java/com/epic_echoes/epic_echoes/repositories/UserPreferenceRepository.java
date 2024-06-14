package com.epic_echoes.epic_echoes.repositories;

import com.epic_echoes.epic_echoes.entities.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserPreferenceRepository extends JpaRepository<UserPreference, UUID> {

    List<UserPreference> findByUserId(UUID userId);

    void deleteByUserIdAndGenreId(UUID userId, UUID genreId);
}
