package com.epic_echoes.epic_echoes.repositories;

import com.epic_echoes.epic_echoes.entities.Genre;
import com.epic_echoes.epic_echoes.entities.Storybook;
import com.epic_echoes.epic_echoes.entities.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class StorybookRepositoryTest {

    @Autowired
    private StorybookRepository storybookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GenreRepository genreRepository;

    private UserInfo user;
    private Genre genre;
    private Storybook storybook;

    @BeforeEach
    void setUp() {
        user = new UserInfo();
        user.setId(UUID.randomUUID());
        user.setUsername("testUser");
        userRepository.save(user);

        genre = new Genre();
        genre.setId(UUID.randomUUID());
        genre.setName("Fantasy");
        genreRepository.save(genre);

        storybook = new Storybook();
        storybook.setId(UUID.randomUUID());
        storybook.setName("Test Storybook");
        storybook.setUser(user);
        storybook.setGenres(List.of(genre));
        storybook.setPrivacy(Storybook.Privacy.EVERYONE_EDIT);
        storybookRepository.save(storybook);
    }

    @Test
    void testFindByUserId() {
        List<Storybook> storybooks = storybookRepository.findByUserId(user.getId());
        assertThat(storybooks).hasSize(1);
        assertThat(storybooks.get(0).getName()).isEqualTo("Test Storybook");
    }

    @Test
    void testFindByGenres() {
        List<Storybook> storybooks = storybookRepository.findByGenres(List.of(genre.getId()));
        assertThat(storybooks).hasSize(1);
        assertThat(storybooks.get(0).getGenres()).contains(genre);
    }

    @Test
    void testFindStorybooksByUserAndPrivacy() {
        List<Storybook> storybooks = storybookRepository.findStorybooksByUserAndPrivacy(user.getId(), Storybook.Privacy.EVERYONE_EDIT);
        assertThat(storybooks).hasSize(1);
        assertThat(storybooks.get(0).getPrivacy()).isEqualTo(Storybook.Privacy.EVERYONE_EDIT);
    }

    @Test
    void testFindStorybooksByUser() {
        List<Storybook> storybooks = storybookRepository.findStorybooksByUser(user.getId());
        assertThat(storybooks).hasSize(1);
    }
}
