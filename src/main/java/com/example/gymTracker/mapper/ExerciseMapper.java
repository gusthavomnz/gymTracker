package com.example.gymTracker.mapper;

import com.example.gymTracker.dto.request.ExerciseRequestDTO;
import com.example.gymTracker.dto.response.ExerciseResponseDTO;
import com.example.gymTracker.model.Exercise;
import com.example.gymTracker.model.TrainingGroup;
import org.springframework.stereotype.Component;

@Component
public class ExerciseMapper {

    public ExerciseResponseDTO toDTO(Exercise exercise) {
        return new ExerciseResponseDTO(
                exercise.getExerciseId(),
                exercise.getName(),
                exercise.getTrainingGroup().getTgId()
        );
    }

    public Exercise toEntity(ExerciseRequestDTO dto, TrainingGroup trainingGroup) {
        Exercise exercise = new Exercise();
        exercise.setName(dto.name());
        exercise.setTrainingGroup(trainingGroup);
        return exercise;
    }

    public void updateEntity(Exercise exercise, ExerciseRequestDTO dto, TrainingGroup trainingGroup) {
        exercise.setName(dto.name());
        exercise.setTrainingGroup(trainingGroup);
    }
}
