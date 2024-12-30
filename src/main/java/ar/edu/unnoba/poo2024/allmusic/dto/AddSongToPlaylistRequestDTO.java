package ar.edu.unnoba.poo2024.allmusic.dto;

public class AddSongToPlaylistRequestDTO {
    public Long getSong_id() {
        return song_id;
    }

    public void setSong_id(Long song_id) {
        this.song_id = song_id;
    }

    private Long song_id;

}
