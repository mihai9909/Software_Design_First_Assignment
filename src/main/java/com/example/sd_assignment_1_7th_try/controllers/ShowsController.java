package com.example.sd_assignment_1_7th_try.controllers;

import com.example.sd_assignment_1_7th_try.models.Show;
import com.example.sd_assignment_1_7th_try.services.SerializationService;
import com.example.sd_assignment_1_7th_try.services.SessionService;
import com.example.sd_assignment_1_7th_try.services.ShowCRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;

@RestController
@RequestMapping("/shows")
@RequiredArgsConstructor
public class ShowsController {
    @Autowired
    private final SessionService sessionService;
    @Autowired
    private final ShowCRUDService showCRUDService;
    @Autowired
    private SerializationService serializationService;
    @GetMapping
    private ResponseEntity<String> findShows(){
        String json = serializationService.serializeAs("JSON", showCRUDService.findAll());

        return ResponseEntity.ok(json);
    }

    @PutMapping("/{id}")
    private ResponseEntity<String> updateShow(@PathVariable Long id, @RequestParam String title,
                                              @RequestParam String genre, @RequestParam Timestamp dateTime,
                                              @RequestParam Integer remainingTickets){
        sessionService.authorizeAdmin();

        if (showCRUDService.updateShow(id, title, genre, dateTime, remainingTickets)) {
            return ResponseEntity.ok("Show updated successfully");
        }

        return ResponseEntity.unprocessableEntity().body("Failed to update show");
    }

    @PostMapping
    public ResponseEntity<String> createShow(@RequestBody Show show) {
        sessionService.authorizeAdmin();

        if (showCRUDService.createShow(show)) {
            return ResponseEntity.ok("Show created successfully");
        }

        return ResponseEntity.unprocessableEntity().body("Failed to create show");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShow(@PathVariable Long id){
        sessionService.authorizeAdmin();

        if(showCRUDService.deleteShow(id)){
            return ResponseEntity.ok("Show deleted successfully");
        }

        return ResponseEntity.unprocessableEntity().body("Failed to delete show");
    }
}
