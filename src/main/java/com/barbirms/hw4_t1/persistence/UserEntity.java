package com.barbirms.hw4_t1.persistence;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class UserEntity {
    @Id
    @Column(name = "user_login")
    public String login;

    public String password;

    @ManyToMany
    @JoinTable( joinColumns = @JoinColumn(name = "user_login"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Set<RoleEntity> roles;

    @Column(unique=true)
    public String email;

    public UserEntity(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }
    public UserEntity() {}
}
