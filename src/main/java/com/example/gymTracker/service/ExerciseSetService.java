package com.example.gymTracker.service;

import com.example.gymTracker.dto.request.ExerciseSetRequestDTO;
import com.example.gymTracker.dto.response.ExerciseSetResponseDTO;
import com.example.gymTracker.mapper.ExerciseSetMapper;
import com.example.gymTracker.model.Exercise;
import com.example.gymTracker.model.ExerciseSet;
import com.example.gymTracker.model.TrainingSession;
import com.example.gymTracker.repository.ExerciseSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExerciseSetService {

    @Autowired
    private ExerciseSetRepository exerciseSetRepository;

    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private TrainingSessionService trainingSessionService;

    @Autowired
    private ExerciseSetMapper exerciseSetMapper;

    @Transactional
    public ExerciseSetResponseDTO saveSet(ExerciseSetRequestDTO dto) {
        TrainingSession session = trainingSessionService.findEntityById(dto.tsId());
        Exercise exercise = exerciseService.findEntityById(dto.exerciseId());

        long countSet = exerciseSetRepository.countByTrainingSessionSessionIdAndExerciseExerciseId(
                session.getSessionId(), exercise.getExerciseId());

        ExerciseSet entity = exerciseSetMapper.toEntity(dto, session, exercise, (int) countSet + 1);
        ExerciseSet saved = exerciseSetRepository.save(entity);
        return exerciseSetMapper.toDTO(saved);
    }

    public List<ExerciseSet> findByUserWithDetails(Long userId) {
        return exerciseSetRepository.findByUserWithDetails(userId);
    }

    public List<ExerciseSet> findByUserAndDateAfterWithDetails(Long userId, LocalDate startDate) {
        return exerciseSetRepository.findByUserAndDateAfterWithDetails(userId, startDate);
    }
}
