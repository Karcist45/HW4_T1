package com.barbirms.hw4_t1.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RevokeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;

    public String token;
}
