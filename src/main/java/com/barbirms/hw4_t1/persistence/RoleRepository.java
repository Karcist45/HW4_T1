package com.barbirms.hw4_t1.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, String> {

    RoleEntity findByUserRole(UserRole role);
}
