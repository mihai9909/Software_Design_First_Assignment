package com.example.sd_assignment_1_7th_try.controllers;

import com.example.sd_assignment_1_7th_try.models.Ticket;
import com.example.sd_assignment_1_7th_try.services.SerializationService;
import com.example.sd_assignment_1_7th_try.services.SessionService;
import com.example.sd_assignment_1_7th_try.services.TicketCRUDService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
    private SerializationService serializationService;
    @GetMapping
    private ResponseEntity<String> findTickets() {
        sessionService.authorizeCashier();

        String json = serializationService.serializeAs("JSON", ticketCRUDService.findAllTickets());
        return ResponseEntity.ok(json);
    }

    @GetMapping("/show/{id}")
    @ResponseBody
    private ResponseEntity<byte[]> findTicketsForShow(@PathVariable Long id) {
        sessionService.authorizeAdmin();

        List<Ticket> tickets = ticketCRUDService.findByShow(id);

        String json = serializationService.serializeAs("JSON", tickets);

        byte[] content = json.getBytes(StandardCharsets.UTF_8);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setContentLength(content.length);
        headers.setContentDispositionFormData("attachment", "tickets.json");

        return new ResponseEntity<>(content, headers, HttpStatus.OK);
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
            return ResponseEntity.ok("Updated successfully");
        }
        return ResponseEntity.badRequest().body("Failed to update");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTicket(@PathVariable Long id){
        sessionService.authorizeCashier();

        if(ticketCRUDService.deleteTicket(id)){
            return ResponseEntity.ok("Deleted successfully");
        }

        return ResponseEntity.badRequest().body("Failed to delete");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleValidationException(ConstraintViolationException ex) {
        List<String> errors = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.toString());
    }
}
