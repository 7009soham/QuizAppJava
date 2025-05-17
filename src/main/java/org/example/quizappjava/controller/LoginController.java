package org.example.quizappjava.controller;

import jakarta.servlet.http.HttpSession;
import org.example.quizappjava.model.Player;
import org.example.quizappjava.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private PlayerRepository playerRepository;

    @PostMapping("/login")
    public String handleLogin(@RequestParam("username") String username, HttpSession session) {
        Player player = playerRepository.findByUsername(username);

        if (player == null) {
            player = new Player();
            player.setUsername(username);
            player.setScore(0);
            player.setRole("PLAYER");
            playerRepository.save(player);
            System.out.println("✅ New player created: " + username);
        } else {
            System.out.println("👤 Existing player logged in: " + username);
        }

        session.setAttribute("player", player);
        session.setAttribute("username", username); // ✅ Must add this
        return "redirect:/select-mode";
    }


    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // login.jsp
    }
}
