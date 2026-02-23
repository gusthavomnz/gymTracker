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
                .map(e -> new ExerciseDTO(
                        e.getExerciseId(),
                        e.getName(),
                        e.getTrainingGroup().getTgId()))
                .toList();
    }


}
