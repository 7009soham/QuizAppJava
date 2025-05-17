package org.example.quizappjava.controller;

import jakarta.servlet.http.HttpSession;
import org.example.quizappjava.model.Lobby;
import org.example.quizappjava.model.Player;
import org.example.quizappjava.repository.PlayerRepository;
import org.example.quizappjava.service.LobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ModeSelectionController {

    @Autowired
    private LobbyService lobbyService;

    @Autowired
    private PlayerRepository playerRepository;

    @PostMapping("/select-mode")
    public String selectMode(@RequestParam String mode,
                             @RequestParam String lobbyType,
                             HttpSession session,
                             Model model) {

        String username = (String) session.getAttribute("username");
        if (username == null || username.isEmpty()) {
            model.addAttribute("error", "Session expired. Please log in again.");
            return "redirect:/login";
        }

        Player player = playerRepository.findByUsername(username);
        if (player == null) {
            model.addAttribute("error", "Player not found. Please register again.");
            return "redirect:/login";
        }

        // Save selected mode to session (for quiz and roast mode logic later)
        session.setAttribute("gameMode", mode);

        switch (lobbyType.toUpperCase()) {
            case "INDIVIDUAL":
                System.out.println("🧍 Individual mode selected by: " + username);
                return "redirect:/quiz";

            case "MULTIPLAYER":
                Lobby multiplayerLobby = lobbyService.assignToPublicLobby(player);
                return "redirect:/lobby?lobbyCode=" + multiplayerLobby.getCode();

            case "1V1":
                Lobby oneVsOneLobby = lobbyService.assignTo1v1(player);
                return "redirect:/lobby?lobbyCode=" + oneVsOneLobby.getCode();

            default:
                model.addAttribute("username", username);
                model.addAttribute("error", "Invalid lobby type selected.");
                return "select-mode";
        }
    }
    @GetMapping("/select-mode")
    public String showModeSelection(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null || username.isEmpty()) {
            return "redirect:/login";
        }
        model.addAttribute("username", username);
        return "selectMode";  // ✅ MATCHES selectMode.jsp
    }
    @GetMapping("/start-quiz")
    public String preventDirectStart() {
        return "redirect:/select-mode";
    }


}
