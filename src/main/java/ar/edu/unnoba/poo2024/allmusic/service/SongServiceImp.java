package ar.edu.unnoba.poo2024.allmusic.service;


import ar.edu.unnoba.poo2024.allmusic.model.Genre;
import ar.edu.unnoba.poo2024.allmusic.model.Song;
import ar.edu.unnoba.poo2024.allmusic.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongServiceImp implements SongService{
    @Autowired
    private SongRepository repository;

    @Override
    public List<Song> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Song> getAllFromUser(Long userId) {
        return repository.getAllFromUser(userId);
    }

    @Override
    public void create(Song song) throws Exception {
        repository.save(song);
    }

    @Override
    public void update(Long id, Song song) {

    }

    @Override
    public Optional<Song> getInstance(Long id) {
        return repository.findById(id);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Song> findByArtistAndGenre(String artistName, Genre genre) {
        return repository.findByArtistAndGenre(artistName, genre);
    }


}
