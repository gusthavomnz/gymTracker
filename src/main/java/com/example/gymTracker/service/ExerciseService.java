package com.example.gymTracker.service;

import com.example.gymTracker.dto.request.ExerciseRequestDTO;
import com.example.gymTracker.dto.response.ExerciseResponseDTO;
import com.example.gymTracker.mapper.ExerciseMapper;
import com.example.gymTracker.model.Exercise;
import com.example.gymTracker.model.TrainingGroup;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.gymTracker.repository.ExerciseRepository;

import java.util.List;

@Service
public class ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private TrainingGroupService trainingGroupService;

    @Autowired
    private ExerciseMapper exerciseMapper;

    @Transactional
    public ExerciseResponseDTO createExercise(ExerciseRequestDTO dto) {
        TrainingGroup trainingGroup = trainingGroupService.findEntityById(dto.trainingGroupId());
        Exercise entity = exerciseMapper.toEntity(dto, trainingGroup);
        Exercise saved = exerciseRepository.save(entity);
        return exerciseMapper.toDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<ExerciseResponseDTO> findAll() {
        return exerciseRepository.findAllWithTrainingGroup().stream()
                .map(exerciseMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ExerciseResponseDTO> findByTrainingGroup(long tgId) {
        return exerciseRepository.findByTrainingGroupWithDetails(tgId).stream()
                .map(exerciseMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ExerciseResponseDTO> findByName(String name) {
        return exerciseRepository.findByNameContainingIgnoreCase(name).stream()
                .map(exerciseMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ExerciseResponseDTO> findByMuscleGroupName(String muscleGroup) {
        return exerciseRepository.findByTrainingGroup_NameContainingIgnoreCase(muscleGroup).stream()
                .map(exerciseMapper::toDTO)
                .toList();
    }

    @Transactional
    public ExerciseResponseDTO updateExercise(Long id, ExerciseRequestDTO dto) {
        Exercise exercise = findEntityById(id);
        TrainingGroup trainingGroup = trainingGroupService.findEntityById(dto.trainingGroupId());
        exerciseMapper.updateEntity(exercise, dto, trainingGroup);
        Exercise saved = exerciseRepository.save(exercise);
        return exerciseMapper.toDTO(saved);
    }

    @Transactional
    public void deleteExercise(Long id) {
        if (!exerciseRepository.existsById(id)) {
            throw new EntityNotFoundException("Exercise not found");
        }
        exerciseRepository.deleteById(id);
    }

    public Exercise findEntityById(Long id) {
        return exerciseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Exercise not found"));
    }

    public List<Exercise> findAllEntitiesByIds(List<Long> ids) {
        return exerciseRepository.findAllById(ids);
    }
}
