package com.epic_echoes.epic_echoes.services;

import com.epic_echoes.epic_echoes.dto.StorybookUserPermissionDTO;
import com.epic_echoes.epic_echoes.entities.StorybookUserPermission;
import com.epic_echoes.epic_echoes.repositories.StorybookUserPermissionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StorybookUserPermissionServiceImpl implements StorybookUserPermissionService {

    private final StorybookUserPermissionRepository permissionRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public StorybookUserPermissionServiceImpl(StorybookUserPermissionRepository permissionRepository, ModelMapper modelMapper) {
        this.permissionRepository = permissionRepository;
        this.modelMapper = modelMapper;
    }

    public List<StorybookUserPermissionDTO> getAllPermissions() {
        List<StorybookUserPermission> permissions = permissionRepository.findAll();
        return permissions.stream()
                .map(item -> modelMapper.map(item, StorybookUserPermissionDTO.class))
                .collect(Collectors.toList());
    }

    public StorybookUserPermissionDTO getPermissionById(UUID id) {
        StorybookUserPermission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found"));
        return modelMapper.map(permission, StorybookUserPermissionDTO.class);
    }

    public StorybookUserPermissionDTO createPermission(StorybookUserPermissionDTO permissionDTO) {
        StorybookUserPermission permission = modelMapper.map(permissionDTO, StorybookUserPermission.class);
        StorybookUserPermission savedPermission = permissionRepository.save(permission);
        return modelMapper.map(savedPermission, StorybookUserPermissionDTO.class);
    }

    @Transactional
    public StorybookUserPermissionDTO updatePermission(UUID id, StorybookUserPermissionDTO permissionDTO) {
        StorybookUserPermission existingPermission = permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found"));
        modelMapper.map(permissionDTO, existingPermission);
        StorybookUserPermission updatedPermission = permissionRepository.save(existingPermission);
        return modelMapper.map(updatedPermission, StorybookUserPermissionDTO.class);
    }

    @Transactional
    public boolean deletePermission(UUID id) {
        if (permissionRepository.existsById(id)) {
            permissionRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
