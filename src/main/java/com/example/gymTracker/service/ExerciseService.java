package com.example.gymTracker.service;

import com.example.gymTracker.dto.ExerciseDTO;
import com.example.gymTracker.model.ExerciseSet;
import jakarta.persistence.EntityNotFoundException;
import com.example.gymTracker.model.Exercise;
import com.example.gymTracker.model.TrainingGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.gymTracker.repository.ExerciseRepository;
import com.example.gymTracker.repository.TrainingGroupRepository;

import java.util.List;


@Service
public class ExerciseService {

    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    TrainingGroupRepository trainingGroupRepository;



    @Transactional
    public ExerciseDTO createExercise(ExerciseDTO exerciseDTO){

        TrainingGroup searchTrainingGroup = trainingGroupRepository.findById(exerciseDTO.getTrainingGroupId()).orElseThrow(()
                -> new EntityNotFoundException("Training Group not found"));

        Exercise newExercise = new Exercise();
        newExercise.setName(exerciseDTO.getName());
        newExercise.setTrainingGroup(searchTrainingGroup);

        exerciseRepository.save(newExercise);

        return new ExerciseDTO(newExercise.getExerciseId(),
                newExercise.getName(),
                newExercise.getTrainingGroup().getTgId());
    }

    public List<ExerciseDTO> findAll() {
        List<Exercise> exercises = exerciseRepository.findAll();

        // Converte a lista de Entidades para uma lista de DTOs
        return exercises.stream()
                .map(e -> new ExerciseDTO(
                        e.getExerciseId(),
                        e.getName(),
                        e.getTrainingGroup().getTgId()))
                .toList();
    }


    public List<ExerciseDTO> findByTrainingGroup(long search){
        List<Exercise> exercises = exerciseRepository.findByTrainingGroup_TgId(search);
        return exercises.stream()
                .map(this::mapToDTO)
                .toList();
    }

    public List<ExerciseDTO> findByName(String name) {
        return exerciseRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::mapToDTO)
                .toList();
    }

    public List<ExerciseDTO> findByMuscleGroupName(String muscleGroup) {
        return exerciseRepository.findByTrainingGroup_NameContainingIgnoreCase(muscleGroup).stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Transactional
    public ExerciseDTO updateExercise(Long id, ExerciseDTO exerciseDTO) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Exercise not found"));
        
        TrainingGroup trainingGroup = trainingGroupRepository.findById(exerciseDTO.getTrainingGroupId())
                .orElseThrow(() -> new EntityNotFoundException("Training Group not found"));

        exercise.setName(exerciseDTO.getName());
        exercise.setTrainingGroup(trainingGroup);
        
        Exercise updatedExercise = exerciseRepository.save(exercise);
        return mapToDTO(updatedExercise);
    }

    @Transactional
    public void deleteExercise(Long id) {
        if (!exerciseRepository.existsById(id)) {
            throw new EntityNotFoundException("Exercise not found");
        }
        exerciseRepository.deleteById(id);
    }

    private ExerciseDTO mapToDTO(Exercise e) {
        return new ExerciseDTO(
                e.getExerciseId(),
                e.getName(),
                e.getTrainingGroup().getTgId());
    }
}
