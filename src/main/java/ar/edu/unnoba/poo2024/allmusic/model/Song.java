package ar.edu.unnoba.poo2024.allmusic.model;

import jakarta.persistence.*;

@Entity
@Table(name="songs")
public class Song {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Genre genre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private MusicArtistUser author;

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
}
