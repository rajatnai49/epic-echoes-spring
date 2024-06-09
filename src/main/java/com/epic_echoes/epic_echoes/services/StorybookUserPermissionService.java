package com.epic_echoes.epic_echoes.services;

import com.epic_echoes.epic_echoes.dto.StorybookUserPermissionDTO;

import java.util.List;
import java.util.UUID;

public interface StorybookUserPermissionService {
    List<StorybookUserPermissionDTO> getAllPermissions();
    StorybookUserPermissionDTO getPermissionById(UUID id);
    StorybookUserPermissionDTO createPermission(StorybookUserPermissionDTO permissionDTO);
    StorybookUserPermissionDTO updatePermission(UUID id, StorybookUserPermissionDTO permissionDTO);
    boolean deletePermission(UUID id);
}
