package com.example.gymTracker.model;


import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name="exercises")
public class Exercise {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_id")
    private Long exerciseId;

    @Column(name = "name")
    private String name;


    @ManyToOne
    @JoinColumn(name = "training_group_id")
    private TrainingGroup trainingGroup;

}
