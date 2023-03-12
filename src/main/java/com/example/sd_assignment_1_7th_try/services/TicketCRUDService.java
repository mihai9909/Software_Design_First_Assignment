package com.example.sd_assignment_1_7th_try.services;

import com.example.sd_assignment_1_7th_try.models.Show;
import com.example.sd_assignment_1_7th_try.models.Ticket;
import com.example.sd_assignment_1_7th_try.repositories.ShowRepository;
import com.example.sd_assignment_1_7th_try.repositories.TicketRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketCRUDService {
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ShowRepository showRepository;

    @Transactional
    public boolean createTicket(Ticket ticket) {
        Optional<Show> showOptional = showRepository.findById(ticket.getShow().getId());
        if (showOptional.isEmpty()) {
            return false;
        }

        Show show = showOptional.get();
        show.setRemainingTickets(show.getRemainingTickets() - ticket.getPlaces());
        try {
            ticketRepository.save(ticket);
            showRepository.save(show);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean updateTicket(Long id, Ticket updatedTicket) {
        Optional<Ticket> existingTicketOptional = ticketRepository.findById(id);
        if (existingTicketOptional.isEmpty()) {
            return false;
        }
        Ticket existingTicket = existingTicketOptional.get();

        Optional<Show> showOptional = showRepository.findById(existingTicket.getShow().getId());
        if (showOptional.isEmpty()) {
            return false;
        }
        Show show = showOptional.get();
        Integer placesDifference = existingTicket.getPlaces() - updatedTicket.getPlaces();
        show.setRemainingTickets(show.getRemainingTickets() + placesDifference);

        try {
            updatedTicket.setId(id);
            ticketRepository.save(updatedTicket);
            showRepository.save(show);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<Ticket> findAllTickets() {
        return ticketRepository.findAll();
    }

    public List<Ticket> findByShow(Show show) {
        return ticketRepository.findByShow(show);
    }
}
