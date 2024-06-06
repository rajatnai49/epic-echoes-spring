package com.epic_echoes.epic_echoes.repositories;


import com.epic_echoes.epic_echoes.entities.UserInfo;
import com.epic_echoes.epic_echoes.helpers.RefreshableCRUDRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends RefreshableCRUDRepository<UserInfo, UUID> {

    public UserInfo findByUsername(String username);
    UserInfo findFirstById(UUID id);

}