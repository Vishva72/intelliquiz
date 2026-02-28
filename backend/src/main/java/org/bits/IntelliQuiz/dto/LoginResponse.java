package org.bits.IntelliQuiz.dto;

public class LoginResponse {
    private String token;
    private String role;
    private String email;

    public LoginResponse(String token, String role, String username) {
        this.token = token;
        this.role = role;
        this.email = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

