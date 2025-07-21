package com.barbirms.hw4_t1.controllers;

import java.util.List;

public class JwtResponse {
    public String token;
    public String type = "jwt";
    public String login;
    public String email;
    public List<String> roles;

    public JwtResponse(String token, String login, String email, List<String> roles) {
        this.token = token;
        this.login = login;
        this.email = email;
        this.roles = roles;
    }

}
