package com.epic_echoes.epic_echoes.repositories;

import com.epic_echoes.epic_echoes.entities.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, UUID> {

}
