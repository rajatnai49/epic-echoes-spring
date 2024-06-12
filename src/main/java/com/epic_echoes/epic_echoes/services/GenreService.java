package com.epic_echoes.epic_echoes.services;

import com.epic_echoes.epic_echoes.dto.GenreDTO;
import com.epic_echoes.epic_echoes.entities.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> findOrCreateGenres(List<GenreDTO> genreDTOs);
    List<Genre> findGenresByName(List<String> genreNames);
    Genre findOrCreateGenre(GenreDTO genreDTO);
}
