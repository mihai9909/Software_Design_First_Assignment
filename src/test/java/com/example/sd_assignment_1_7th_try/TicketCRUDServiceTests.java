package com.example.sd_assignment_1_7th_try;

import com.example.sd_assignment_1_7th_try.models.Show;
import com.example.sd_assignment_1_7th_try.models.Ticket;
import com.example.sd_assignment_1_7th_try.repositories.ShowRepository;
import com.example.sd_assignment_1_7th_try.repositories.TicketRepository;
import com.example.sd_assignment_1_7th_try.services.TicketCRUDService;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TicketCRUDServiceTests {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private ShowRepository showRepository;

    private TicketCRUDService ticketCRUDService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ticketCRUDService = new TicketCRUDService(ticketRepository, showRepository);
    }

    @Test
    public void createTicket_shouldReturnFalse_whenShowDoesntExist() {
        java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf("2007-09-23 10:10:10.0");
        Show show = new Show(1L, "Show 1", "EDM", timestamp, 100);
        Ticket ticket = new Ticket(1L, show, 10.0, 30);
        when(showRepository.findById(1L)).thenReturn(Optional.of(show));
        when(showRepository.save(any())).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));

        boolean created = ticketCRUDService.createTicket(ticket);

        Assertions.assertFalse(created);
    }
}
