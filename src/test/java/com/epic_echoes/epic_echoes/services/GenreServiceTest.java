package com.epic_echoes.epic_echoes.services;

import com.epic_echoes.epic_echoes.dto.GenreDTO;
import com.epic_echoes.epic_echoes.entities.Genre;
import com.epic_echoes.epic_echoes.repositories.GenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class GenreServiceTest {

    private GenreRepository genreRepository;
    private ModelMapper modelMapper;
    private GenreService genreService;

    private GenreDTO genreDTO1;
    private GenreDTO genreDTO2;
    private Genre genre1;
    private Genre genre2;

    @BeforeEach
    void setUp() {
        genreRepository = mock(GenreRepository.class);
        modelMapper = mock(ModelMapper.class);
        genreService = new GenreServiceImpl(genreRepository, modelMapper);

        genreDTO1 = new GenreDTO(UUID.randomUUID(), "Action");
        genreDTO2 = new GenreDTO(UUID.randomUUID(), "Fantasy");

        genre1 = new Genre(UUID.randomUUID(), "action");
        genre2 = new Genre(UUID.randomUUID(), "fantasy");

        when(modelMapper.map(genreDTO1, Genre.class)).thenReturn(genre1);
        when(modelMapper.map(genreDTO2, Genre.class)).thenReturn(genre2);
    }

    @Test
    void testFindOrCreateGenre_ExistingGenre() {
        when(genreRepository.findByName(anyString())).thenReturn(Optional.of(genre1));

        Genre result = genreService.findOrCreateGenre(genreDTO1);
        assertThat(result).isEqualTo(genre1);
        verify(genreRepository, never()).save(any(Genre.class));
    }

    @Test
    void testFindOrCreateGenre_NewGenre() {
        when(genreRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(genreRepository.save(any(Genre.class))).thenReturn(genre1);

        Genre result = genreService.findOrCreateGenre(genreDTO1);
        assertThat(result).isEqualTo(genre1);
        verify(genreRepository, times(1)).save(any(Genre.class));
    }

    @Test
    void testFindOrCreateGenres() {
        when(genreRepository.findByName("action")).thenReturn(Optional.of(genre1));
        when(genreRepository.findByName("fantasy")).thenReturn(Optional.empty());
        when(genreRepository.save(genre2)).thenReturn(genre2);

        List<GenreDTO> genreDTOs = Arrays.asList(genreDTO1, genreDTO2);
        List<Genre> result = genreService.findOrCreateGenres(genreDTOs);

        assertThat(result).containsExactlyInAnyOrder(genre1, genre2);
        verify(genreRepository, times(1)).save(genre2);
    }

    @Test
    void testFindGenresByName() {
        List<String> genreNames = Arrays.asList("Action", "Fantasy");
        List<Genre> genres = Arrays.asList(genre1, genre2);
        when(genreRepository.findByNameIn(anyList())).thenReturn(genres);

        List<Genre> result = genreService.findGenresByName(genreNames);

        assertThat(result).isEqualTo(genres);
        verify(genreRepository, times(1)).findByNameIn(anyList());
    }
}
