package model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "exercise_sets") // Padronizado com o plural e snake_case
public class ExerciseSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "set_id") // Nome sugerido para manter o padrão
    private Long setId;

    @Column(name = "set_number") // No UML costuma ser o número da série (1, 2, 3...)
    private Integer setNumber;

    private Integer repetitions; // Seguindo o UML

    private BigDecimal weight;

    @ManyToOne
    @JoinColumn(name = "session_id") // Liga com a TrainingSession
    private TrainingSession trainingSession;

    @ManyToOne
    @JoinColumn(name = "exercise_id") // Liga com o Exercise
    private Exercise exercise;
}