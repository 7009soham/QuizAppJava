package org.example.quizappjava.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lobby")
public class Lobby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code; // Lobby code
    private String type; // MULTIPLAYER / 1V1
    private boolean isOpen;
    private boolean isPrivate;
    private String hostUsername;
    private LocalDateTime expiryTime;

    @ManyToMany
    @JoinTable(
            name = "lobby_players", // ✅ join table name
            joinColumns = @JoinColumn(name = "lobby_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id")
    )
    private List<Player> players = new ArrayList<>();

    public Lobby() {}

    // --- Getters and Setters ---

    public Long getId() { return id; }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public boolean isOpen() { return isOpen; }

    public void setOpen(boolean open) { isOpen = open; }

    public boolean isPrivate() { return isPrivate; }

    public void setPrivate(boolean aPrivate) { isPrivate = aPrivate; }

    public String getHostUsername() { return hostUsername; }

    public void setHostUsername(String hostUsername) { this.hostUsername = hostUsername; }

    public LocalDateTime getExpiryTime() { return expiryTime; }

    public void setExpiryTime(LocalDateTime expiryTime) { this.expiryTime = expiryTime; }

    public List<Player> getPlayers() { return players; }

    public void setPlayers(List<Player> players) { this.players = players; }

    public void addPlayer(Player player) {
        if (!players.contains(player)) {
            players.add(player);
        }
    }
}
