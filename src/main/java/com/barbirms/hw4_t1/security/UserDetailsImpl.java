package com.barbirms.hw4_t1.security;

import com.barbirms.hw4_t1.persistence.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private final UserEntity user;

    public UserDetailsImpl(UserEntity user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.userRole.toString()))
                .collect(Collectors.toList());
    }


    @Override
    public String getPassword() {
        return user.password;
    }

    @Override
    public String getUsername() {
        return user.login;
    }

    public String getEmail() {
        return user.email;
    }
}
