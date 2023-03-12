package com.example.sd_assignment_1_7th_try.controllers;

import com.example.sd_assignment_1_7th_try.models.Ticket;
import com.example.sd_assignment_1_7th_try.services.SerializationService;
import com.example.sd_assignment_1_7th_try.services.SessionService;
import com.example.sd_assignment_1_7th_try.services.TicketCRUDService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketsController {
    @Autowired
    private TicketCRUDService ticketCRUDService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private SerializationService jsonSerializationService;
    @GetMapping
    private ResponseEntity<String> findTickets() throws JsonProcessingException {
        sessionService.authorizeCashier();

        String json = jsonSerializationService.serializeAs("JSON", ticketCRUDService.findAllTickets());
        return ResponseEntity.ok(json);
    }

    @PostMapping("/sell")
    private ResponseEntity<String> createTicket(@RequestBody Ticket ticket){
        sessionService.authorizeCashier();

        if(ticketCRUDService.createTicket(ticket))
            return ResponseEntity.ok("Ticket sold");
        return ResponseEntity.unprocessableEntity().body("Verify ticket info");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTicket(@PathVariable Long id, @RequestBody Ticket ticket) {
        sessionService.authorizeCashier();

        if (ticketCRUDService.updateTicket(id, ticket)) {
            return ResponseEntity.ok("Updated sucessfully");
        }
        return ResponseEntity.badRequest().body("Failed to update");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleValidationException(ConstraintViolationException ex) {
        List<String> errors = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.toString());
    }
}
