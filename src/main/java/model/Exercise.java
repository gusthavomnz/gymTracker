package model;


import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class Exercise {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long exercise_id;


    @Column(name = "name")
    private String name;


    @ManyToOne
    @JoinColumn(name = "tg_id")
    private TrainingGroup trainingGroup;

}
