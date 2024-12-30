package ar.edu.unnoba.poo2024.allmusic.dto;

import ar.edu.unnoba.poo2024.allmusic.model.Genre;
import ar.edu.unnoba.poo2024.allmusic.model.MusicArtistUser;

public class SongResponseDTO {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public MusicArtistUser getAuthor() {
        return author;
    }

    public void setAuthor(MusicArtistUser author) {
        this.author = author;
    }

    private Long id;
    private String name;
    private String description;
    private Genre genre;
    private MusicArtistUser author;

}
