package com.barbirms.hw4_t1.controllers;

public class LogoutRequest {
    public String accessToken;
    public String refreshToken;

    public LogoutRequest(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
