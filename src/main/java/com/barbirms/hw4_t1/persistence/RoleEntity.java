package com.barbirms.hw4_t1.persistence;

import jakarta.persistence.*;

@Entity
public class RoleEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "role_id")
    public int id;

    @Enumerated(EnumType.STRING)
    public UserRole userRole;

    public RoleEntity() {}

    public RoleEntity(UserRole role) {
        this.userRole = role;
    }
}
