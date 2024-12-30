package ar.edu.unnoba.poo2024.allmusic.service;

import ar.edu.unnoba.poo2024.allmusic.model.Playlist;
import ar.edu.unnoba.poo2024.allmusic.model.Song;
import ar.edu.unnoba.poo2024.allmusic.repository.PlaylistRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlaylistSerivceImp implements PlaylistService{
    @Autowired
    private PlaylistRepository repository;
    @Override
    public List<Playlist> getAll() {
        return repository.findAll();
    }

    @Override
    public void create(Playlist playlist) throws Exception {
        this.repository.save(playlist);
    }

    @Override
    public Optional<Playlist> findById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public void update(Playlist playlist) throws Exception {
        this.repository.save(playlist);
    }

    @Override
    public void remove(Playlist playlist) throws Exception {
        this.repository.delete(playlist);
    }

    @Override
    public void addSong(Playlist playlist, Song song) throws Exception {
        if (playlist.getSongs() == null) {
            playlist.setSongs(new ArrayList<>());
        }
        if (!playlist.getSongs().contains(song)) {
            playlist.getSongs().add(song);
        }
        this.repository.save(playlist);
    }
    @Override
    public void removeSong(Playlist playlist, Song song) throws Exception {
        if (playlist.getSongs() == null) {
            playlist.setSongs(new ArrayList<>());
        }
        if (playlist.getSongs().contains(song)) {
            playlist.getSongs().remove(song);
        }
        this.repository.save(playlist);
    }

    @Override
    public List<Playlist> getAllFromUser(Long userId) {
        return repository.getAllFromUser(userId);
    }
}
