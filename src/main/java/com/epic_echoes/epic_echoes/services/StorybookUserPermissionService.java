package com.epic_echoes.epic_echoes.services;

import com.epic_echoes.epic_echoes.dto.StorybookDTO;
import com.epic_echoes.epic_echoes.dto.StorybookUserPermissionDTO;
import com.epic_echoes.epic_echoes.dto.UserResponse;

import java.util.List;
import java.util.UUID;

public interface StorybookUserPermissionService {
    List<StorybookUserPermissionDTO> getAllPermissions();
    StorybookUserPermissionDTO getPermissionById(UUID id);
    StorybookUserPermissionDTO createPermission(StorybookUserPermissionDTO permissionDTO);
    StorybookUserPermissionDTO updatePermission(UUID id, StorybookUserPermissionDTO permissionDTO);
    List<StorybookDTO> filterStorybooksBasedOnPrivacy(UUID userId, String privacy);
    List<UserResponse> filterUsersBasedOnPrivacy(UUID storybookId, String privacy);
    boolean deletePermission(UUID id);
}
