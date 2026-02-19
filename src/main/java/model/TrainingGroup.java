package model;


import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "trainingGroup")
public class TrainingGroup {


    @Column(name = "tg_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tg_id;

    @Column(name = "name")
    private String name;


}
