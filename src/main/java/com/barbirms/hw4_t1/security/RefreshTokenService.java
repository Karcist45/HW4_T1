package com.barbirms.hw4_t1.security;

import com.barbirms.hw4_t1.persistence.TokenEntity;
import com.barbirms.hw4_t1.persistence.TokenRepository;
import com.barbirms.hw4_t1.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private int REFRESH_DURATION = 1200000000;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<TokenEntity> findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public TokenEntity refreshToken(String login) {
        if (tokenRepository.findByUser(userRepository.findByLogin(login).get()).isPresent()) {
            tokenRepository.delete(tokenRepository.findByUser(userRepository.findByLogin(login).get()).get());
        }
        TokenEntity newToken = new TokenEntity();

        newToken.user = userRepository.findByLogin(login).get();
        newToken.expires = Instant.now().plusMillis(REFRESH_DURATION);
        newToken.token = UUID.randomUUID().toString();
        tokenRepository.save(newToken);
        return newToken;
    }

    public TokenEntity checkExpired(TokenEntity token) {
        if (Instant.now().isAfter(token.expires)) {
            tokenRepository.delete(token);
            throw new RuntimeException("Token Expired");
        }
        return token;
    }

    public void deleteToken(String token) {
        tokenRepository.delete(tokenRepository.findByToken(token).get());
    }
}
