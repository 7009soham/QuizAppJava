package org.example.quizappjava.repository;

import org.example.quizappjava.model.Lobby;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LobbyRepository extends JpaRepository<Lobby, Long> {
    Optional<Lobby> findFirstByTypeAndIsOpen(String type, boolean isOpen); // ✅ For matchmaking
    Optional<Lobby> findByCode(String code); // ✅ For joining private/public lobbies
}
