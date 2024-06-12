package com.epic_echoes.epic_echoes.services;

import com.epic_echoes.epic_echoes.dto.GenreDTO;
import com.epic_echoes.epic_echoes.dto.StorybookDTO;
import com.epic_echoes.epic_echoes.entities.Genre;
import com.epic_echoes.epic_echoes.entities.Storybook;
import com.epic_echoes.epic_echoes.entities.UserInfo;
import com.epic_echoes.epic_echoes.repositories.StorybookRepository;
import com.epic_echoes.epic_echoes.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class StorybookServiceTest {

    private StorybookRepository storybookRepository;
    private UserRepository userRepository;
    private GenreService genreService;
    private ModelMapper modelMapper;
    private StorybookServiceImpl storybookService;

    private UserInfo user;
    private Genre genre;
    private Storybook storybook;

    @BeforeEach
    void setUp() {
        storybookRepository = mock(StorybookRepository.class);
        userRepository = mock(UserRepository.class);
        genreService = mock(GenreService.class);
        modelMapper = new ModelMapper();
        storybookService = new StorybookServiceImpl(storybookRepository, userRepository, genreService, modelMapper);

        user = new UserInfo();
        user.setId(UUID.randomUUID());
        user.setUsername("testUser");

        genre = new Genre();
        genre.setId(UUID.randomUUID());
        genre.setName("Fantasy");

        storybook = new Storybook();
        storybook.setId(UUID.randomUUID());
        storybook.setName("Test Storybook");
        storybook.setUser(user);
        storybook.setGenres(List.of(genre));
        storybook.setPrivacy(Storybook.Privacy.EVERYONE_EDIT);

        when(storybookRepository.findById(storybook.getId())).thenReturn(Optional.of(storybook));
        when(storybookRepository.findByUserId(user.getId())).thenReturn(List.of(storybook));
        when(storybookRepository.findByGenres(List.of(genre.getId()))).thenReturn(List.of(storybook));
        when(storybookRepository.findStorybooksByUserAndPrivacy(user.getId(), Storybook.Privacy.EVERYONE_EDIT)).thenReturn(List.of(storybook));
        when(storybookRepository.findStorybooksByUser(user.getId())).thenReturn(List.of(storybook));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(genreService.findOrCreateGenres(anyList())).thenReturn(List.of(genre));
    }

    @Test
    void testGetAllStorybooks() {
        List<StorybookDTO> storybooks = storybookService.getAllStorybooks();
        assertThat(storybooks).isEmpty();
    }

    @Test
    void testGetStorybookById() {
        StorybookDTO storybookDTO = storybookService.getStorybookById(storybook.getId());
        assertThat(storybookDTO.getName()).isEqualTo("Test Storybook");
    }

    @Test
    void testGetStorybooksByUserId() {
        List<StorybookDTO> storybooks = storybookService.getStorybooksByUserId(user.getId());
        assertThat(storybooks).hasSize(1);
        assertThat(storybooks.get(0).getName()).isEqualTo("Test Storybook");
    }

    @Test
    void testGetStorybooksByGenres() {
        List<StorybookDTO> storybooks = storybookService.getStorybooksByGenres(List.of(genre.getId()));
        assertThat(storybooks).hasSize(1);
        assertThat(storybooks.get(0).getGenres()).extracting("name").contains("Fantasy");
    }

    @Test
    void testCreateStorybook() {
        StorybookDTO storybookDTO = new StorybookDTO();
        storybookDTO.setId(UUID.randomUUID());
        storybookDTO.setName("New Storybook");
        storybookDTO.setUserId(user.getId());
        storybookDTO.setGenres(List.of(new GenreDTO(UUID.randomUUID(), "Fantasy")));
        storybookDTO.setPlot("Test plot of the storybook.");
        storybookDTO.setMaxChapterLength(1000l);
        storybookDTO.setMinChapterLength(100l);
        storybookDTO.setPrivacy(String.valueOf(Storybook.Privacy.EVERYONE_EDIT));

        StorybookDTO createdStorybook = storybookService.createStorybook(storybookDTO);
        assertThat(createdStorybook.getName()).isEqualTo("New Storybook");
        verify(storybookRepository, times(1)).save(any(Storybook.class));
    }

    @Test
    void testUpdateStorybook() {
        StorybookDTO storybookDTO = new StorybookDTO();
        storybookDTO.setId(UUID.randomUUID());
        storybookDTO.setName("Updated Storybook");
        storybookDTO.setUserId(user.getId());
        storybookDTO.setGenres(List.of(new GenreDTO(UUID.randomUUID(), "Fantasy")));

        StorybookDTO updatedStorybook = storybookService.updateStorybook(storybook.getId(), storybookDTO);
        assertThat(updatedStorybook.getName()).isEqualTo("Updated Storybook");
        verify(storybookRepository, times(1)).save(any(Storybook.class));
    }

    @Test
    void testDeleteStorybookById() {
        when(storybookRepository.existsById(storybook.getId())).thenReturn(true);

        boolean isDeleted = storybookService.deleteStorybookById(storybook.getId());
        assertThat(isDeleted).isTrue();
        verify(storybookRepository, times(1)).deleteById(storybook.getId());
    }
}
