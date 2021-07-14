package com.example.aihomeservice.connect;

public class TokenAccessGetting {
    private String tokeString;
    private static TokenAccessGetting instance;

    public static TokenAccessGetting getInstance() {
        if (instance == null) {
            instance = new TokenAccessGetting();
        }
        return instance;
    }

    private TokenAccessGetting() {
    }

    public void setToken(String tokeString) {
        this.tokeString = tokeString;
    }

    public String getToken() {
        return tokeString;
    }
}
