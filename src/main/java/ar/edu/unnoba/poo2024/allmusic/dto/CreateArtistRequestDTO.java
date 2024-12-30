package ar.edu.unnoba.poo2024.allmusic.dto;

public class CreateArtistRequestDTO extends CreateUserRequestDTO{
    public String getArtisticName() {
        return artisticName;
    }

    public void setArtisticName(String artisticName) {
        this.artisticName = artisticName;
    }

    private String artisticName;
}
