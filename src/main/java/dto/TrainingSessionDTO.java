package dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import model.ExerciseSet;
import model.TrainingGroup;
import model.User;

import java.time.LocalDate;
import java.util.List;
@Data
public class TrainingSessionDTO {


    private long TsId;
    private LocalDate date;

    private String notes;

    private long userId;

    private long tgId;

    private List<ExerciseSetDTO> exerciseSets;

}
