package com.example.sd_assignment_1_7th_try.models;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import org.hibernate.annotations.TimeZoneStorage;
import org.hibernate.validator.constraints.Range;

import java.sql.Timestamp;

@Entity
@Table(name = "shows")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "genre")
    private String genre;
    @Column(name = "date_time")
    private Timestamp dateTime;
    @Column(name = "remaining_tickets")
    @Range(min = 0, message = "Remaining tickets must be greater than or equal to 0")
    private Integer remainingTickets;
}
