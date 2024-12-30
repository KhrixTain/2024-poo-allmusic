package ar.edu.unnoba.poo2024.allmusic.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("music_enthusiast")
public class MusicEnthusiastUser extends User{
    @Override
    public boolean canCreateSongs() {
        return false;
    }
}
