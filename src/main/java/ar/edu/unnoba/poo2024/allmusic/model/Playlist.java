package ar.edu.unnoba.poo2024.allmusic.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "playlists")
public class Playlist {
    public Long getId() {
        return id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;


    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @ManyToOne()
    @JoinColumn(name="owner_id", referencedColumnName = "id")
    private User owner;

   public Playlist() {
        this.songs = new ArrayList<>();
    }

    public List<Song> getSongs() {
        return this.songs;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    @ManyToMany()
    @JoinTable(
            name="playlist_song",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id")
    )
    private List<Song> songs;
}
