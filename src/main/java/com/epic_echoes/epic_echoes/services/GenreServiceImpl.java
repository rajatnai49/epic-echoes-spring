package com.epic_echoes.epic_echoes.services;

import com.epic_echoes.epic_echoes.dto.GenreDTO;
import com.epic_echoes.epic_echoes.entities.Genre;
import com.epic_echoes.epic_echoes.repositories.GenreRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository, ModelMapper modelMapper) {
        this.genreRepository = genreRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Genre> findOrCreateGenres(List<GenreDTO> genreDTOs) {
        return genreDTOs.stream()
                .map(this::findOrCreateGenre)
                .collect(Collectors.toList());
    }

    @Override
    public List<Genre> findGenresByName(List<String> genreNames) {
        List<String> lowerCaseNames = genreNames.stream().map(String::toLowerCase).collect(Collectors.toList());
        return genreRepository.findByNameIn(lowerCaseNames);
    }

    @Override
    public Genre findOrCreateGenre(GenreDTO genreDTO) {
        String genreName = genreDTO.getName().toLowerCase();
        Optional<Genre> existingGenre = genreRepository.findByName(genreName);
        if (existingGenre.isPresent()) {
            return existingGenre.get();
        } else {
            Genre genre = modelMapper.map(genreDTO, Genre.class);
            genre.setName(genreName);
            return genreRepository.save(genre);
        }
    }
}
