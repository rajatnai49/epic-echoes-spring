package com.epic_echoes.epic_echoes.controllers;

import com.epic_echoes.epic_echoes.dto.StorybookDTO;
import com.epic_echoes.epic_echoes.services.StorybookService;
import com.epic_echoes.epic_echoes.services.GenreService;
import com.epic_echoes.epic_echoes.services.StorybookUserPermissionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(StorybookController.class)
public class StorybookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StorybookService storybookService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private StorybookUserPermissionService permissionService;

    private StorybookDTO storybookDTO;
    private UUID storybookId;

    @BeforeEach
    void setUp() {
        storybookId = UUID.randomUUID();
        storybookDTO = new StorybookDTO();
        storybookDTO.setId(storybookId);
        storybookDTO.setName("Test Storybook");
    }

    @Test
    void testGetAllStorybooks() throws Exception {
        when(storybookService.getAllStorybooks()).thenReturn(Collections.singletonList(storybookDTO));

        mockMvc.perform(get("/api/v1/storybooks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Storybook"));
    }

    @Test
    void testGetStorybookById() throws Exception {
        when(storybookService.getStorybookById(storybookId)).thenReturn(storybookDTO);

        mockMvc.perform(get("/api/v1/storybooks/{id}", storybookId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Storybook"));
    }

    @Test
    void testGetStorybooksByUserId() throws Exception {
        when(storybookService.getStorybooksByUserId(storybookDTO.getUserId())).thenReturn(Collections.singletonList(storybookDTO));

        mockMvc.perform(get("/api/v1/storybooks/user/{userId}", storybookDTO.getUserId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Storybook"));
    }

    @Test
    void testGetStorybooksByGenres() throws Exception {
        when(storybookService.getStorybooksByGenres(anyList())).thenReturn(Collections.singletonList(storybookDTO));

        mockMvc.perform(get("/api/v1/storybooks/genres")
                        .param("genres", "Fantasy"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Storybook"));
    }


    @Test
    void testDeleteStorybookById() throws Exception {
        when(storybookService.deleteStorybookById(storybookId)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/storybooks/{id}", storybookId))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
