package com.example.gymTracker.model;


import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "training_groups")
public class TrainingGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tg_id")
    private long tgId;

    @Column(name = "name")
    private String name;


}
