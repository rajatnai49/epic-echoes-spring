package com.epic_echoes.epic_echoes.services;

import com.epic_echoes.epic_echoes.entities.RefreshToken;
import com.epic_echoes.epic_echoes.entities.UserInfo;
import com.epic_echoes.epic_echoes.repositories.RefreshTokenRepository;
import com.epic_echoes.epic_echoes.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    UserRepository userRepository;

    public RefreshToken createRefreshToken(String username){
        UserInfo userInfo = userRepository.findByUsername(username);

        Optional<RefreshToken> existingToken = refreshTokenRepository.findByUserInfo(userInfo);

        if (existingToken.isPresent()) {
            RefreshToken refreshToken = existingToken.get();
            refreshToken.setExpiryDate(Instant.now().plusMillis(600000));
            refreshToken.setToken(UUID.randomUUID().toString());
            return refreshTokenRepository.save(refreshToken);
        } else {
            RefreshToken refreshToken = RefreshToken.builder()
                    .userInfo(userInfo)
                    .token(UUID.randomUUID().toString())
                    .expiryDate(Instant.now().plusMillis(600000))
                    .build();
            return refreshTokenRepository.save(refreshToken);
        }
    }

    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return token;
    }
}
