package org.project4.backend.service.impl;

import org.modelmapper.ModelMapper;
import org.project4.backend.dto.TokenDTO;
import org.project4.backend.entity.TokenEntity;
import org.project4.backend.repository.TokenRepository;
import org.project4.backend.repository.UserRepository;
import org.project4.backend.service.TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class TokenServiceIMPL implements TokenService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public void saveToken(String token) {
        LocalDateTime now = LocalDateTime.now();
        TokenEntity tokenEntity = new TokenEntity();

        tokenEntity.setCreatedAt(now);
        LocalDateTime expiresAt = now.plus(Duration.ofHours(1));
        tokenEntity.setExpiresAt(expiresAt);
        tokenEntity.setResetToken(token);
        tokenRepository.save(tokenEntity);
    }

    @Override
    public void logoutToken(Long id) {
        LocalDateTime now = LocalDateTime.now();
            TokenEntity tokenEntity = tokenRepository.findById(id).orElseThrow(() -> new RuntimeException("khoong tim thay token nay"));
            tokenEntity.setExpiresAt(now);
            tokenRepository.save(tokenEntity);
    }


    @Override
    public TokenDTO getByid(Long id) {
        return null;
    }

    @Override
    public Boolean validateToken(String token) {
        List<TokenEntity> checktoken = tokenRepository.findByResetToken(token);
        LocalDateTime now = LocalDateTime.now();
        for (TokenEntity item : checktoken) {
            if (item.getExpiresAt().isAfter(now)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean getByToken(String token) {
         Boolean tokenEntities = tokenRepository.existsByResetToken(token);
        return tokenEntities;
    }
}
