package org.example.quizappjava.controller;

import jakarta.servlet.http.HttpSession;
import org.example.quizappjava.model.Lobby;
import org.example.quizappjava.model.Player;
import org.example.quizappjava.repository.LobbyRepository;
import org.example.quizappjava.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class LobbyController {

    @Autowired
    private LobbyRepository lobbyRepository;

    @Autowired
    private PlayerRepository playerRepository;

    // Show the lobby room
    @GetMapping("/lobby")
    public String showLobby(@RequestParam("lobbyCode") String lobbyCode,
                            HttpSession session,
                            Model model,
                            RedirectAttributes redirectAttributes) {

        String username = (String) session.getAttribute("username");
        if (username == null || username.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Session expired. Please log in again.");
            return "redirect:/login";
        }

        Optional<Lobby> optionalLobby = lobbyRepository.findByCode(lobbyCode);
        if (optionalLobby.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Lobby not found.");
            return "redirect:/select-mode";
        }

        Lobby lobby = optionalLobby.get();

        boolean isMember = lobby.getPlayers().stream()
                .anyMatch(player -> player.getUsername().equals(username));
        if (!isMember) {
            redirectAttributes.addFlashAttribute("error", "You are not a part of this lobby.");
            return "redirect:/select-mode";
        }

        boolean isHost = username.equals(lobby.getHostUsername());

        model.addAttribute("lobby", lobby);
        model.addAttribute("players", lobby.getPlayers());
        model.addAttribute("isHost", isHost);
        model.addAttribute("username", username);

        if (lobby.getType().equalsIgnoreCase("1v1") && lobby.getPlayers().size() == 2) {
            return "redirect:/quiz?lobbyCode=" + lobbyCode;
        }

        return "lobby";
    }

    // Host starts the quiz manually
    @PostMapping("/start-quiz")
    public String startQuiz(@RequestParam String lobbyCode,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {

        String username = (String) session.getAttribute("username");
        if (username == null) return "redirect:/login";

        Optional<Lobby> optionalLobby = lobbyRepository.findByCode(lobbyCode);
        if (optionalLobby.isEmpty()) return "redirect:/select-mode";

        Lobby lobby = optionalLobby.get();

        if (!username.equals(lobby.getHostUsername())) {
            redirectAttributes.addFlashAttribute("error", "Only the host can start the quiz.");
            return "redirect:/lobby?lobbyCode=" + lobbyCode;
        }

        return "redirect:/quiz?lobbyCode=" + lobbyCode;
    }

    // Join a private lobby using code
    @PostMapping("/lobby/join-private")
    public String joinPrivateLobby(@RequestParam String code,
                                   HttpSession session,
                                   RedirectAttributes redirectAttributes) {

        String username = (String) session.getAttribute("username");
        if (username == null) {
            redirectAttributes.addFlashAttribute("error", "Session expired. Please log in.");
            return "redirect:/login";
        }

        Optional<Lobby> optionalLobby = lobbyRepository.findByCode(code);
        if (optionalLobby.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Invalid or expired lobby code.");
            return "redirect:/lobby/join-private";
        }

        Lobby lobby = optionalLobby.get();
        if (!lobby.isPrivate()) {
            redirectAttributes.addFlashAttribute("error", "This is not a private lobby.");
            return "redirect:/lobby/join-private";
        }

        Player player = playerRepository.findByUsername(username);
        if (player == null) {
            redirectAttributes.addFlashAttribute("error", "User not found.");
            return "redirect:/login";
        }

        boolean alreadyInLobby = lobby.getPlayers().stream()
                .anyMatch(p -> p.getUsername().equals(username));
        if (!alreadyInLobby) {
            lobby.addPlayer(player);
            lobbyRepository.save(lobby);
        }

        redirectAttributes.addFlashAttribute("message", "Joined private lobby!");
        return "redirect:/lobby?lobbyCode=" + code;
    }

    // Join a public lobby from dropdown
    @PostMapping("/lobby/join")
    public String joinPublicLobby(@RequestParam String lobbyCode,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {

        String username = (String) session.getAttribute("username");
        if (username == null) return "redirect:/login";

        Optional<Lobby> optionalLobby = lobbyRepository.findByCode(lobbyCode);
        if (optionalLobby.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Lobby not found.");
            return "redirect:/select-mode";
        }

        Lobby lobby = optionalLobby.get();
        Player player = playerRepository.findByUsername(username);
        if (player == null) return "redirect:/login";

        if (!lobby.getPlayers().contains(player)) {
            if (lobby.getPlayers().size() < 8) {
                lobby.addPlayer(player);
                lobbyRepository.save(lobby);
                redirectAttributes.addFlashAttribute("message", "Joined public lobby!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Lobby is full!");
                return "redirect:/select-mode";
            }
        }

        return "redirect:/lobby?lobbyCode=" + lobbyCode;
    }
}
