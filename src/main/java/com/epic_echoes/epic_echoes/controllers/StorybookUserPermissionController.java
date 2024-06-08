package com.epic_echoes.epic_echoes.controllers;

import com.epic_echoes.epic_echoes.dto.StorybookUserPermissionDTO;
import com.epic_echoes.epic_echoes.services.StorybookUserPermissionService;
import com.epic_echoes.epic_echoes.services.StorybookUserPermissionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/permissions")
public class StorybookUserPermissionController {

    private final StorybookUserPermissionServiceImpl permissionService;

    @Autowired
    public StorybookUserPermissionController(StorybookUserPermissionServiceImpl permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping
    public ResponseEntity<List<StorybookUserPermissionDTO>> getAllPermissions() {
        List<StorybookUserPermissionDTO> permissions = permissionService.getAllPermissions();
        return ResponseEntity.ok(permissions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StorybookUserPermissionDTO> getPermissionById(@PathVariable UUID id) {
        StorybookUserPermissionDTO permission = permissionService.getPermissionById(id);
        return ResponseEntity.ok(permission);
    }

    @PostMapping
    public ResponseEntity<StorybookUserPermissionDTO> createPermission(@RequestBody StorybookUserPermissionDTO permissionDTO) {
        StorybookUserPermissionDTO createdPermission = permissionService.createPermission(permissionDTO);
        return ResponseEntity.ok(createdPermission);
    }
}
