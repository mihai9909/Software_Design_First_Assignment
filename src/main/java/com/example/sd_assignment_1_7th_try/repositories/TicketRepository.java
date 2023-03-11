package com.example.sd_assignment_1_7th_try.repositories;

import com.example.sd_assignment_1_7th_try.models.Show;
import com.example.sd_assignment_1_7th_try.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByShow(Show show);
}
