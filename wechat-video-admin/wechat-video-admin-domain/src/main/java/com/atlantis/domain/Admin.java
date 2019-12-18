package com.atlantis.domain;

public class Admin {
    private String token;
    private String username;
    private String password;

    public Admin() {
    }

    public Admin(String token, String username, String password) {
        this.token = token;
        this.username = username;
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
