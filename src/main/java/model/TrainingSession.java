package model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Table(name = "trainingSession")
public class TrainingSession {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ts_id;

    private LocalDate date;

    private String notes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "tg_id")
    private TrainingGroup trainingGroup;

    @OneToMany(mappedBy = "trainingSession", cascade = CascadeType.ALL)
    private List<ExerciseSet> exerciseSets = new ArrayList<>();

    // O MÉTODO HELPER:
    public void addExerciseSet(ExerciseSet set) {
        // 1. Adiciona o set à lista da Sessão
        this.exerciseSets.add(set);

        // 2. Avisa o set que ele PERTENCE a esta Sessão específica (this)
        set.setEs_id(this.getTs_id());
    }

}
