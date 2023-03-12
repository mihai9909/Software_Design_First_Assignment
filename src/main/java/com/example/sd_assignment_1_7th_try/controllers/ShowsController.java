package com.example.sd_assignment_1_7th_try.controllers;

import com.example.sd_assignment_1_7th_try.models.Show;
import com.example.sd_assignment_1_7th_try.services.CashierCRUDService;
import com.example.sd_assignment_1_7th_try.services.SessionService;
import com.example.sd_assignment_1_7th_try.services.ShowCRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.sql.Timestamp;

@RestController
@RequestMapping("/shows")
@RequiredArgsConstructor
public class ShowsController {
    @Autowired
    private final SessionService sessionService;
    @Autowired
    private final ShowCRUDService showCRUDService;
    @GetMapping
    private ResponseEntity<String> findShows(){
        return ResponseEntity.ok(showCRUDService.findAll());
    }

    @PutMapping("/{id}")
    private ResponseEntity<String> updateShow(@PathVariable Long id, @RequestParam String title,
                                              @RequestParam String genre, @RequestParam Timestamp dateTime,
                                              @RequestParam Integer maxTickets){
        sessionService.authorizeAdmin();

        if (showCRUDService.updateShow(id, title, genre, dateTime, maxTickets)) {
            return ResponseEntity.ok("Show updated successfully");
        }

        return ResponseEntity.badRequest().body("Failed to update show");
    }

    @PostMapping
    public ResponseEntity<String> createShow(@RequestBody Show show) {
        sessionService.authorizeAdmin();

        if (showCRUDService.createShow(show)) {
            return ResponseEntity.ok("Show created successfully");
        }

        return ResponseEntity.badRequest().body("Failed to create show");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShow(@PathVariable Long id){
        sessionService.authorizeAdmin();

        if(showCRUDService.deleteShow(id)){
            return ResponseEntity.ok("Show deleted successfully");
        }

        return ResponseEntity.badRequest().body("Failed to delete show");
    }
}
