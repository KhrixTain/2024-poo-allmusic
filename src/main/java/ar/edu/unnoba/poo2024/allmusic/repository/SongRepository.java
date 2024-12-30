package ar.edu.unnoba.poo2024.allmusic.repository;

import ar.edu.unnoba.poo2024.allmusic.model.Genre;
import ar.edu.unnoba.poo2024.allmusic.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {

    @Query("SELECT s FROM Song s WHERE s.author.id = :userId")
    List<Song> getAllFromUser(@Param("userId") Long userId);

    @Query("SELECT s FROM Song s WHERE (:artistName IS NULL OR s.author.artisticName = :artistName) AND (:genre IS NULL OR s.genre = :genre)")
    List<Song> findByArtistAndGenre(@Param("artistName") String artistName, @Param("genre") Genre genre);

}
