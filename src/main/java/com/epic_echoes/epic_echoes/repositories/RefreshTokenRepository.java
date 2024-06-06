package com.epic_echoes.epic_echoes.repositories;

import com.epic_echoes.epic_echoes.entities.UserInfo;
import com.epic_echoes.epic_echoes.helpers.RefreshableCRUDRepository;
import org.springframework.stereotype.Repository;
import com.epic_echoes.epic_echoes.entities.RefreshToken;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends RefreshableCRUDRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByUserInfo(UserInfo userInfo);
    Optional<RefreshToken> findByToken(String token);
}