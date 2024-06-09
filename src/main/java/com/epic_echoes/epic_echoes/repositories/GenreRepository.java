package com.epic_echoes.epic_echoes.repositories;

import com.epic_echoes.epic_echoes.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GenreRepository extends JpaRepository<Genre, UUID> {
    Optional<Genre> findByName(String name);
    List<Genre> findByNameIn(List<String> names);
}
