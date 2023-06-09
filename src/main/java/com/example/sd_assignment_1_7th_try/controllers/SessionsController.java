package com.example.sd_assignment_1_7th_try.controllers;

import com.example.sd_assignment_1_7th_try.models.User;
import com.example.sd_assignment_1_7th_try.services.SessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class SessionsController {
    @Autowired
    private final SessionService sessionService;

    @PostMapping("/login")
    public ResponseEntity<String> setCookie(@RequestParam("email") String email,
                                            @RequestParam("password") String password,
                                            HttpServletResponse response) {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", email);
        credentials.put("password", password);
        if (sessionService.authenticate(credentials)) {
            Cookie cookie = sessionService.generateSessionCookie(email);
            response.addCookie(cookie);
            return ResponseEntity.ok("Authenticated!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }
    @DeleteMapping("/logout")
    public ResponseEntity<String> deleteCookie(HttpServletResponse response){
        Cookie cookie = new Cookie("_session_id",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.ok("Logged out!");
    }

    @GetMapping("/current-user")
    public ResponseEntity<String> readCookie() {
        User currentUser = sessionService.getCurrentUser();
        if(currentUser == null)
            return ResponseEntity.ok("Not logged in!");
        return ResponseEntity.ok("Email: " + currentUser.getEmail() + " Role: " + currentUser.getRole());
    }
}
