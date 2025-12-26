package com.barbirms.hw4_t1.controllers;

import java.util.List;

public class UserDTO {
    String login;

    String password;

    String email;

    List<String> roles;

    public UserDTO(String login, String password, String email, List<String> roles) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }
}
