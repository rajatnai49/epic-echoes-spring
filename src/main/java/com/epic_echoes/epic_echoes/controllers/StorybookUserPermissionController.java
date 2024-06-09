package com.epic_echoes.epic_echoes.controllers;

import com.epic_echoes.epic_echoes.dto.StorybookUserPermissionDTO;
import com.epic_echoes.epic_echoes.services.StorybookUserPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/permissions")
public class StorybookUserPermissionController {

    private final StorybookUserPermissionService permissionService;

    @Autowired
    public StorybookUserPermissionController(StorybookUserPermissionService permissionService) {
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

    @PutMapping("/{id}")
    public ResponseEntity<StorybookUserPermissionDTO> updatePermission(@PathVariable UUID id, @RequestBody StorybookUserPermissionDTO permissionDTO) {
        StorybookUserPermissionDTO updatedPermission = permissionService.updatePermission(id, permissionDTO);
        return ResponseEntity.ok(updatedPermission);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable UUID id) {
        boolean isDeleted = permissionService.deletePermission(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}
