package ar.edu.unnoba.poo2024.allmusic.dto;

public class PlaylistSimplifiedResponseDTO {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSongCount() {
        return songCount;
    }

    public void setSongCount(int songCount) {
        this.songCount = songCount;
    }

    private String name;
    private int songCount;


}
