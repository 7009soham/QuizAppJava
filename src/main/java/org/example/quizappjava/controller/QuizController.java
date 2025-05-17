package org.example.quizappjava.controller;

import jakarta.servlet.http.HttpSession;
import org.example.quizappjava.model.Lobby;
import org.example.quizappjava.model.Player;
import org.example.quizappjava.repository.LobbyRepository;
import org.example.quizappjava.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class QuizController {

    @Autowired
    private LobbyRepository lobbyRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @GetMapping("/quiz")
    public String startQuiz(@RequestParam(required = false) String lobbyCode,
                            HttpSession session,
                            Model model) {

        String username = (String) session.getAttribute("username");
        String mode = (String) session.getAttribute("gameMode");

        if (username == null || mode == null) {
            model.addAttribute("error", "Session expired. Please log in again.");
            return "redirect:/login";
        }

        Player player = playerRepository.findByUsername(username);
        if (player == null) return "redirect:/login";

        // Validate lobby if multiplayer/1v1
        if (lobbyCode != null) {
            Optional<Lobby> optionalLobby = lobbyRepository.findByCode(lobbyCode);
            if (optionalLobby.isEmpty()) {
                model.addAttribute("error", "Lobby not found.");
                return "redirect:/select-mode";
            }

            Lobby lobby = optionalLobby.get();
            boolean isMember = lobby.getPlayers().stream()
                    .anyMatch(p -> p.getUsername().equals(username));
            if (!isMember) {
                model.addAttribute("error", "You're not part of this lobby.");
                return "redirect:/select-mode";
            }

            model.addAttribute("lobby", lobby);
        }

        // For now, send a dummy question
        model.addAttribute("question", "What is the capital of India?");
        model.addAttribute("options", new String[]{"Delhi", "Mumbai", "Kolkata", "Chennai"});
        model.addAttribute("mode", mode);
        model.addAttribute("username", username);

        return "quiz";
    }
}
