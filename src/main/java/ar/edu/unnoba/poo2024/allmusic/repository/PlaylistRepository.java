package ar.edu.unnoba.poo2024.allmusic.repository;

import ar.edu.unnoba.poo2024.allmusic.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaylistRepository  extends JpaRepository<Playlist, Long> {
    @Query("SELECT s FROM Playlist s WHERE s.owner.id = :userId")
    List<Playlist> getAllFromUser(@Param("userId") Long userId);
}
