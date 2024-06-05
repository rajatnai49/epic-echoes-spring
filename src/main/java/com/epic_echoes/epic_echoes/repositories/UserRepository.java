package com.epic_echoes.epic_echoes.repositories;


import com.epic_echoes.epic_echoes.entities.UserInfo;
import com.epic_echoes.epic_echoes.helpers.RefreshableCRUDRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends RefreshableCRUDRepository<UserInfo, Long> {

    public UserInfo findByUsername(String username);
    UserInfo findFirstById(Long id);

}