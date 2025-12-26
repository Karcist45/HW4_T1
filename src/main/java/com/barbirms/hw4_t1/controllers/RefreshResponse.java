package com.barbirms.hw4_t1.controllers;

public class RefreshResponse {
    public String accessToken;

    public String refreshedToken;

    public String tokenType = "Bearer";

    public RefreshResponse(String accessToken, String refreshedToken) {
        this.accessToken = accessToken;
        this.refreshedToken = refreshedToken;
    }
}
