package org.example.quizappjava.repository;

import org.example.quizappjava.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Player findByUsername(String username);
}
