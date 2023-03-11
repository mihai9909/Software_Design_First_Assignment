package com.example.sd_assignment_1_7th_try.services;

import com.example.sd_assignment_1_7th_try.models.Show;
import com.example.sd_assignment_1_7th_try.models.Ticket;
import com.example.sd_assignment_1_7th_try.repositories.ShowRepository;
import com.example.sd_assignment_1_7th_try.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ShowRepository showRepository;

    public boolean createTicket(Ticket ticket) {
        // Check if the show exists
        Optional<Show> showOptional = showRepository.findById(ticket.getShow().getId());
        if (showOptional.isEmpty()) {
            return false;
        }

        // Set the show for the ticket
        ticket.setShow(showOptional.get());

        try {
            ticketRepository.save(ticket);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String findAllTickets() {
        return ticketRepository.findAll().toString();
    }

    public List<Ticket> findByShow(Show show) {
        return ticketRepository.findByShow(show);
    }
}
