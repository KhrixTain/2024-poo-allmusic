package ar.edu.unnoba.poo2024.allmusic.service;

import ar.edu.unnoba.poo2024.allmusic.model.Playlist;
import ar.edu.unnoba.poo2024.allmusic.model.Song;

import java.util.List;
import java.util.Optional;

public interface PlaylistService {
    public List<Playlist> getAll();
    public void create(Playlist playlist) throws Exception;
    public Optional<Playlist> findById(Long id);
    public void update(Playlist playlist) throws Exception;
    public void remove(Playlist playlist) throws Exception;
    public void addSong(Playlist playlist, Song song) throws Exception;
    public void removeSong(Playlist playlist, Song song) throws Exception;

    List<Playlist> getAllFromUser(Long id);
}
