package com.example.sd_assignment_1_7th_try.repositories;

import com.example.sd_assignment_1_7th_try.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findUserById(Long id);

    List<User> findUserByRole(String role);
}