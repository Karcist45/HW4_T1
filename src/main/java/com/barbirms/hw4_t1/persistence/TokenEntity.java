package com.barbirms.hw4_t1.persistence;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class TokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "token_id")
    public int id;

    @OneToOne
    @JoinColumn(name = "token_user_id", referencedColumnName = "user_login")
    public UserEntity user;

    @Column(nullable = false, unique = true)
    public String token;

    public Instant expires;
}
