package com.example.gymTracker.service;


import com.example.gymTracker.dto.ExerciseSetDTO;
import com.example.gymTracker.dto.TrainingSessionDTO;
import com.example.gymTracker.model.Exercise;
import com.example.gymTracker.model.ExerciseSet;
import com.example.gymTracker.model.TrainingSession;
import com.example.gymTracker.repository.ExerciseRepository;
import com.example.gymTracker.repository.ExerciseSetRepository;
import com.example.gymTracker.repository.TrainingSessionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExerciseSetService {

@Autowired
    ExerciseSetRepository exerciseSetRepository;
@Autowired
    ExerciseRepository exerciseRepository;
@Autowired
TrainingSessionRepository trainingSessionRepository;




@Transactional
public ResponseEntity saveSet(ExerciseSetDTO exerciseSetDTO) {
    // BUSCA A SESSÃO DE TREINO ATUAL:
    TrainingSession currentSession = trainingSessionRepository.findById(exerciseSetDTO.getTs_id()).orElseThrow();
    Exercise currentExercise = exerciseRepository.findById(exerciseSetDTO.getExerciseId()).orElseThrow();

    // HORA DE IMPLEMENTAR A LOGICA PARA SABER SE JA EXISTE ALGUMA SERIE DESSE EXERCICIO:
    long countSet = exerciseSetRepository.countByTrainingSessionSessionIdAndExerciseExerciseId(currentSession.getSessionId(), currentExercise.getExerciseId());

    ExerciseSet newSet = new ExerciseSet();
    newSet.setTrainingSession(currentSession);
    newSet.setExercise(currentExercise);
    newSet.setWeight(exerciseSetDTO.getWeight());
    newSet.setRepetitions(exerciseSetDTO.getReps());
    newSet.setSetNumber((int)countSet + 1);

    ExerciseSet savedSet = exerciseSetRepository.save(newSet);

    ExerciseSetDTO response = new ExerciseSetDTO();
    response.setExerciseId(savedSet.getExercise().getExerciseId());
    response.setSetNumber(savedSet.getSetNumber());
    response.setWeight(savedSet.getWeight());
    response.setReps(savedSet.getRepetitions());
    response.setTs_id(savedSet.getTrainingSession().getSessionId());
    return ResponseEntity.ok(response);

}

}
