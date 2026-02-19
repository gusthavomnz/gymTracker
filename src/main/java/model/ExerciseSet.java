package model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "exerciseSet")
public class ExerciseSet {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "es_id")
    private long es_id;

    @Column(name = "numberSets")
    private Integer numberSets;

    @Column(name = "reps")
    private Integer reps;

    @Column(name = "weight")
    private BigDecimal weight;
}
