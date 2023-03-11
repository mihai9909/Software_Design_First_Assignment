package com.example.sd_assignment_1_7th_try.repositories;

import com.example.sd_assignment_1_7th_try.models.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {
    List<Show> findAllByGenre(String genre);
}
