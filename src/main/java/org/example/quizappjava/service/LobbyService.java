package org.example.quizappjava.service;

import org.example.quizappjava.model.Lobby;
import org.example.quizappjava.model.Player;
import org.example.quizappjava.repository.LobbyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class LobbyService {

    @Autowired
    private LobbyRepository lobbyRepository;

    private static final int MAX_PUBLIC_PLAYERS = 8;
    private static final int MAX_1V1_PLAYERS = 2;

    /**
     * Assigns a player to a public lobby. Creates a new one if full or not found.
     */
    public Lobby assignToPublicLobby(Player player) {
        Optional<Lobby> optionalLobby = lobbyRepository.findFirstByTypeAndIsOpen("MULTIPLAYER", true);
        Lobby lobby;

        if (optionalLobby.isPresent()) {
            lobby = optionalLobby.get();

            // Check if already in lobby
            if (!lobby.getPlayers().contains(player)) {
                // If lobby is full, create new
                if (lobby.getPlayers().size() >= MAX_PUBLIC_PLAYERS) {
                    lobby.setOpen(false);
                    lobbyRepository.save(lobby); // Save the closed lobby
                    System.out.println("⚠️ Lobby full. Creating new one.");
                    return createNewPublicLobby(player);
                }

                lobby.addPlayer(player);
            } else {
                System.out.println("🔁 Player already in public lobby: " + lobby.getCode());
            }

            return lobbyRepository.save(lobby);
        }

        return createNewPublicLobby(player);
    }

    /**
     * Creates a brand new public multiplayer lobby.
     */
    private Lobby createNewPublicLobby(Player player) {
        Lobby lobby = new Lobby();
        lobby.setType("MULTIPLAYER");
        lobby.setOpen(true);
        lobby.setPrivate(false);
        lobby.setCode(generateRandomCode());
        lobby.setHostUsername(player.getUsername());
        lobby.setExpiryTime(LocalDateTime.now().plusMinutes(10));
        lobby.addPlayer(player);

        System.out.println("🆕 Created new public lobby: " + lobby.getCode());
        return lobbyRepository.save(lobby);
    }

    /**
     * Assigns a player to a 1v1 lobby. Creates one if not found or full.
     */
    public Lobby assignTo1v1(Player player) {
        Optional<Lobby> optionalLobby = lobbyRepository.findFirstByTypeAndIsOpen("1V1", true);
        Lobby lobby = optionalLobby.orElse(null);

        if (lobby == null || lobby.getPlayers().size() >= MAX_1V1_PLAYERS) {
            if (lobby != null) {
                lobby.setOpen(false);
                lobbyRepository.save(lobby); // close old
            }
            lobby = createNew1v1Lobby(player);
        } else {
            if (!lobby.getPlayers().contains(player)) {
                lobby.addPlayer(player);
                System.out.println("⚔️ Joined 1v1 lobby: " + lobby.getCode());
            }
            if (lobby.getPlayers().size() >= MAX_1V1_PLAYERS) {
                lobby.setOpen(false);
            }
        }

        return lobbyRepository.save(lobby);
    }

    /**
     * Creates a new 1v1 lobby.
     */
    private Lobby createNew1v1Lobby(Player player) {
        Lobby lobby = new Lobby();
        lobby.setType("1V1");
        lobby.setOpen(true);
        lobby.setPrivate(false);
        lobby.setCode(generateRandomCode());
        lobby.setHostUsername(player.getUsername());
        lobby.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        lobby.addPlayer(player);

        System.out.println("⚔️ Created new 1v1 lobby: " + lobby.getCode());
        return lobbyRepository.save(lobby);
    }

    /**
     * Generates a short random code (uppercase).
     */
    private String generateRandomCode() {
        return UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}
