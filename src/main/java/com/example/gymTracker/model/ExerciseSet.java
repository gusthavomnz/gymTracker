package com.example.gymTracker.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "exercise_sets")
public class ExerciseSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "set_id")
    private Long setId;

    @Column(name = "set_number")
    private Integer setNumber;

    private Integer repetitions;

    private BigDecimal weight;

    @ManyToOne
    @JoinColumn(name = "session_id") // Liga com a TrainingSession
    private TrainingSession trainingSession;

    @ManyToOne
    @JoinColumn(name = "exercise_id") // Liga com o Exercise
    private Exercise exercise;
}