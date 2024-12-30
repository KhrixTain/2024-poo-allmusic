package ar.edu.unnoba.poo2024.allmusic.service;

import ar.edu.unnoba.poo2024.allmusic.model.Genre;
import ar.edu.unnoba.poo2024.allmusic.model.Song;

import java.util.List;
import java.util.Optional;

public interface SongService {
    public List<Song> getAll();
    public List<Song> getAllFromUser(Long userId);
    public void create(Song song) throws Exception;
    public void update(Long id, Song song);
    public Optional<Song> getInstance(Long id);
    public void delete(Long id);
    List<Song> findByArtistAndGenre(String artistName, Genre genre);
}
