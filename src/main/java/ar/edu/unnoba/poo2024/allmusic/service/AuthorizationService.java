package ar.edu.unnoba.poo2024.allmusic.service;

import ar.edu.unnoba.poo2024.allmusic.model.User;

public interface AuthorizationService {
    public User authorize(String token) throws Exception;
}
