package ar.edu.unnoba.poo2024.allmusic.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("music_artist")
public class MusicArtistUser extends User{
    public String getArtisticName() {
        return artisticName;
    }

    public void setArtisticName(String artisticName) {
        this.artisticName = artisticName;
    }

    private String artisticName;

    @Override
    public boolean canCreateSongs() {
        return true;
    }
}
