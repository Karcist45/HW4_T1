package com.barbirms.hw4_t1.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Integer> {
    Optional<TokenEntity> findByToken(String token);

    Optional<TokenEntity> findByUser(UserEntity user);

    void deleteByToken(String token);
}
