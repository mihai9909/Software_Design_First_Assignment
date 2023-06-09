package com.example.sd_assignment_1_7th_try.services;

import com.example.sd_assignment_1_7th_try.models.Show;
import com.example.sd_assignment_1_7th_try.models.Ticket;
import com.example.sd_assignment_1_7th_try.repositories.ShowRepository;
import com.example.sd_assignment_1_7th_try.repositories.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
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

        Show show = existingTicket.getShow();
        if (show == null) {
            return false;
        }

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

    @Transactional
    public boolean deleteTicket(Long id){
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);
        if(ticketOptional.isEmpty())
            return false;

        Ticket ticket = ticketOptional.get();
        Show show = ticket.getShow();
        show.setRemainingTickets(ticket.getPlaces() + show.getRemainingTickets());
        try {
            ticketRepository.save(ticket);
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

    public List<Ticket> findByShow(Long id) {
        Optional<Show> show = showRepository.findById(id);
        if(show.isEmpty())
            return null;

        return ticketRepository.findByShow(show.get());
    }
}
