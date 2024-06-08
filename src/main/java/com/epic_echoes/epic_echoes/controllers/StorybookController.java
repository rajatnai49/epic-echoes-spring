package com.epic_echoes.epic_echoes.controllers;

import com.epic_echoes.epic_echoes.dto.StorybookDTO;
import com.epic_echoes.epic_echoes.dto.StorybookUserPermissionDTO;
import com.epic_echoes.epic_echoes.services.StorybookServiceImpl;
import com.epic_echoes.epic_echoes.services.StorybookUserPermissionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/storybooks")
public class StorybookController {

    private final StorybookServiceImpl storybookService;
    private final StorybookUserPermissionServiceImpl permissionService;

    @Autowired
    public StorybookController(StorybookServiceImpl storybookService, StorybookUserPermissionServiceImpl permissionService) {
        this.storybookService = storybookService;
        this.permissionService = permissionService;
    }

    @GetMapping
    public ResponseEntity<List<StorybookDTO>> getAllStorybooks() {
        List<StorybookDTO> storybooks = storybookService.getAllStorybooks();
        return ResponseEntity.ok(storybooks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StorybookDTO> getStorybookById(@PathVariable UUID id) {
        StorybookDTO storybook = storybookService.getStorybookById(id);
        return ResponseEntity.ok(storybook);
    }

    @PostMapping
    public ResponseEntity<StorybookDTO> createStorybook(@RequestBody StorybookDTO storybookDTO) {
        System.out.println("Story book request called" + storybookDTO);
        StorybookDTO createdStorybook = storybookService.createStorybook(storybookDTO);
        return ResponseEntity.ok(createdStorybook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StorybookDTO> updateStorybook(@PathVariable UUID id, @RequestBody StorybookDTO storybookDTO) {
        StorybookDTO updatedStorybook = storybookService.updateStorybook(id, storybookDTO);
        return ResponseEntity.ok(updatedStorybook);
    }

    @PutMapping("/{id}/permissions")
    public ResponseEntity<StorybookUserPermissionDTO> updatePermission(@PathVariable UUID id, @RequestBody StorybookUserPermissionDTO permissionDTO) {
        StorybookUserPermissionDTO updatedPermission = permissionService.updatePermission(id, permissionDTO);
        return ResponseEntity.ok(updatedPermission);
    }
}
