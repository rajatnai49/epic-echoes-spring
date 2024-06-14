package com.epic_echoes.epic_echoes.repositories;

import com.epic_echoes.epic_echoes.entities.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class GenreRepositoryTest {
    @Autowired
    private GenreRepository genreRepository;

    private Genre genre1;
    private Genre genre2;

    @BeforeEach
    void setUp() {
        genre1 = new Genre();
        genre1.setName("Action");
        genreRepository.save(genre1);

        genre2 = new Genre();
        genre2.setName("Fantasy");
        genreRepository.save(genre2);
    }

    @Test
    void testFindByName_Found() {
        Optional<Genre> foundGenre = genreRepository.findByName("Action");
        assertThat(foundGenre).isPresent();
        assertThat(foundGenre.get().getName()).isEqualTo("Action");
    }

    @Test
    void testFinByName_NotFound() {
        Optional<Genre> foundGenre = genreRepository.findByName("NotExist");
        assertThat(foundGenre).isNotPresent();
    }

    @Test
    void testFindByNameIn_Found() {
        List<Genre> foundGenres = genreRepository.findByNameIn(List.of("Action", "Fantasy"));
        assertThat(foundGenres).hasSize(2);
        assertThat(foundGenres).extracting("name").containsExactlyInAnyOrder("Action", "Fantasy");
    }

    @Test
    void testFindByNameIn_PartialFound() {
        List<Genre> foundGenres = genreRepository.findByNameIn(List.of("Action", "NotExist"));
        assertThat(foundGenres).hasSize(1);
        assertThat(foundGenres.getFirst().getName()).isEqualTo("Action");
    }

    @Test
    void testFindByNameIn_NotFound() {
        List<Genre> foundGenres = genreRepository.findByNameIn(List.of("NotExist"));
        assertThat(foundGenres).isEmpty();
    }
}
