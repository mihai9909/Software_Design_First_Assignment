package com.example.sd_assignment_1_7th_try.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import lombok.NonNull;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "tickets")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id", nullable = false)
    @NotNull
    private Show show;

    @Column(name = "unit_price")
    @Range(min = 0, message = "Unit price must be greater than or equal to 0")
    private Double unitPrice;

    @Column(name = "places")
    @Range(min = 0, message = "Places must be greater than or equal to 0")
    private Integer places;

}
