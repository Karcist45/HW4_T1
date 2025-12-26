package com.barbirms.hw4_t1.persistence;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class UserEntity {
    @Id
    @Column(name = "user_login")
    public String login;

    public String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( joinColumns = @JoinColumn(name = "user_login"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles = new HashSet<>();

    @Column(unique=true)
    public String email;

    public UserEntity(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public UserEntity() {}

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }
}
