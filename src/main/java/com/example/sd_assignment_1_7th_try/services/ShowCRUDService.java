package com.example.sd_assignment_1_7th_try.services;

import com.example.sd_assignment_1_7th_try.repositories.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShowCRUDService {
    @Autowired
    private ShowRepository showRepository;
    public String findAll(){
        return showRepository.findAllByGenre("Rock").toString();
    }
}
