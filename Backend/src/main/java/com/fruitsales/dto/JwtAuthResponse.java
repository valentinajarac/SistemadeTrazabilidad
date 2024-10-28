package com.fruitsales.dto;

import lombok.Data;

@Data
public class JwtAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String username;

    public JwtAuthResponse(String accessToken, String username) {
        this.accessToken = accessToken;
        this.username = username;
    }
}