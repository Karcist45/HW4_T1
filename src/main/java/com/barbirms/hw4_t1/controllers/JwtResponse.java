package com.barbirms.hw4_t1.controllers;

import java.util.List;

public class JwtResponse {
    public String access_token;
    public String refresh_token;
    public String type = "Bearer";
    public String login;
    public String email;
    public List<String> roles;

    public JwtResponse(String token, String refresh_token, String login, String email, List<String> roles) {
        this.access_token = token;
        this.refresh_token = refresh_token;
        this.login = login;
        this.email = email;
        this.roles = roles;
    }

}
