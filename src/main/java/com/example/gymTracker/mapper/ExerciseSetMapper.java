package com.example.gymTracker.mapper;

import com.example.gymTracker.dto.request.ExerciseSetRequestDTO;
import com.example.gymTracker.dto.response.ExerciseSetResponseDTO;
import com.example.gymTracker.model.Exercise;
import com.example.gymTracker.model.ExerciseSet;
import com.example.gymTracker.model.TrainingSession;
import org.springframework.stereotype.Component;

@Component
public class ExerciseSetMapper {

    public ExerciseSetResponseDTO toDTO(ExerciseSet set) {
        return new ExerciseSetResponseDTO(
                set.getTrainingSession().getSessionId(),
                set.getExercise().getExerciseId(),
                set.getSetNumber(),
                set.getRepetitions(),
                set.getWeight()
        );
    }

    public ExerciseSet toEntity(ExerciseSetRequestDTO dto, TrainingSession session, Exercise exercise, int setNumber) {
        ExerciseSet set = new ExerciseSet();
        set.setTrainingSession(session);
        set.setExercise(exercise);
        set.setWeight(dto.weight());
        set.setRepetitions(dto.reps());
        set.setSetNumber(setNumber);
        return set;
    }
}
