package com.example.sd_assignment_1_7th_try.services;

import com.example.sd_assignment_1_7th_try.models.Show;
import com.example.sd_assignment_1_7th_try.repositories.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShowCRUDService {
    @Autowired
    private ShowRepository showRepository;
    public String findAll(){
        return showRepository.findAll().toString();
    }

    public boolean updateShow(Long id,
                              String title,
                              String genre,
                              Timestamp dateTime,
                              Integer maxTickets){
        Optional<Show> showOptional = showRepository.findById(id);
        if (showOptional.isEmpty()) {
            return false;
        }

        Show show = showOptional.get();
        show.setTitle(title);
        show.setGenre(genre);
        show.setDateTime(dateTime);
        show.setMaxTickets(maxTickets);

        try {
            showRepository.save(show);
            return true;
        } catch (Exception e){
            return false;
        }

    }

    public boolean createShow(Show show) {
        try {
            showRepository.save(show);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteShow(Long id) {
        Optional<Show> showOptional = showRepository.findById(id);
        if(showOptional.isEmpty())
            return false;
        
        Show show = showOptional.get();
        showRepository.delete(show);
        return true;
    }
}
