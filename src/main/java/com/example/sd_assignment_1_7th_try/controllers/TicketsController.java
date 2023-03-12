package com.example.sd_assignment_1_7th_try.controllers;

import com.example.sd_assignment_1_7th_try.services.CashierCRUDService;
import com.example.sd_assignment_1_7th_try.services.SessionService;
import com.example.sd_assignment_1_7th_try.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketsController {
    @Autowired
    private TicketService ticketService;
    @Autowired
    private SessionService sessionService;
    @GetMapping
    private ResponseEntity<String> findTickets(){
        sessionService.authorizeCashier();

        return ResponseEntity.ok(ticketService.findAllTickets());
    }
}
