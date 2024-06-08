package com.epic_echoes.epic_echoes.repositories;

import com.epic_echoes.epic_echoes.entities.StorybookUserPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StorybookUserPermissionRepository extends JpaRepository<StorybookUserPermission, UUID> {
}
