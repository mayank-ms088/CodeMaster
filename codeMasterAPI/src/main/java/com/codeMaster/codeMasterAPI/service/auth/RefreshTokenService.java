package com.codeMaster.codeMasterAPI.service.auth;

import com.codeMaster.codeMasterAPI.dto.auth.RefreshTokenRequest;
import com.codeMaster.codeMasterAPI.models.RefreshToken;
import com.codeMaster.codeMasterAPI.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

    @Autowired
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateToken() {
        RefreshToken refreshToken=new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());
        return refreshTokenRepository.saveAndFlush(refreshToken);
    }

    public void deleteToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

    public void validateToken(String refreshToken) {
        refreshTokenRepository.findByToken(refreshToken).orElseThrow(()->new RuntimeException("Invalid Refresh Token."));
    }
}
