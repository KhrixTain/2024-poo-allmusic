package ar.edu.unnoba.poo2024.allmusic.repository;

import ar.edu.unnoba.poo2024.allmusic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsername(@Param("username") String username);
}
