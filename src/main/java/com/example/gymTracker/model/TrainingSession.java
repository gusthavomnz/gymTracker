package com.example.gymTracker.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*CREATE TABLE training_sessions (
        session_id BIGINT NOT NULL AUTO_INCREMENT,
        user_id BIGINT NOT NULL,
        tg_id BIGINT NOT NULL,
        date DATE NOT NULL,
        notes VARCHAR(255),
PRIMARY KEY (session_id),
CONSTRAINT fk_session_user FOREIGN KEY (user_id) REFERENCES users (user_id),
CONSTRAINT fk_session_group FOREIGN KEY (tg_id) REFERENCES training_groups (tg_id)
        ) ENGINE=InnoDB;

 */
@Data
@Entity
@Table(name = "training_sessions")
public class TrainingSession {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Long sessionId;

    @Column(name = "ts_name")
    private String name;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "notes")
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
        this.exerciseSets.add(set);
        set.setTrainingSession(this);
    }

}
