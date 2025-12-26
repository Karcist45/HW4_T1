package com.barbirms.hw4_t1.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevokeRepository extends JpaRepository<RevokeEntity, Integer> {
    boolean existsByToken(String token);
}
