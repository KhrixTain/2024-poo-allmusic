package ar.edu.unnoba.poo2024.allmusic.dto;

public class AuthenticationResponseDTO {
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;

    public AuthenticationResponseDTO(String token) {
        this.token = token;
    }
}
