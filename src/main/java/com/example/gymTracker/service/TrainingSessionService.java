package com.example.gymTracker.service;


import com.example.gymTracker.dto.ExerciseSetDTO;
import com.example.gymTracker.dto.TrainingSessionDTO;
import com.example.gymTracker.model.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.gymTracker.config.AuthService;
import com.example.gymTracker.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainingSessionService {

    @Autowired
    TrainingSessionRepository trainingSessionRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private TrainingGroupRepository trainingGroupRepository;


    @Transactional
    public TrainingSessionDTO createTrainingSession(TrainingSessionDTO trainingSessionDTO){
        User user = authService.getAuthenticatedUser();

        TrainingGroup trainingGroup = trainingGroupRepository.findById(trainingSessionDTO.getTgId())
                .orElseThrow(() -> new EntityNotFoundException("Training Group not found"));

        // Inicia o processo de Instanciamento da entidade:
        TrainingSession trainingSession = new TrainingSession();
        trainingSession.setName(trainingSessionDTO.getName());
        trainingSession.setUser(user); // <-- User que veio do Token
        trainingSession.setTrainingGroup(trainingGroup);
        trainingSession.setDate(trainingSessionDTO.getDate());
        trainingSession.setNotes(trainingSessionDTO.getNotes());

        TrainingSession savedSession = trainingSessionRepository.save(trainingSession);

        return convertDTO(savedSession);
    }

    @Transactional
    public TrainingSessionDTO convertDTO(TrainingSession trainingSesssaion) {
        TrainingSessionDTO responseDTO = new TrainingSessionDTO();

        responseDTO.setTsId(trainingSesssaion.getSessionId());
        responseDTO.setDate(trainingSesssaion.getDate());
        responseDTO.setNotes(trainingSesssaion.getNotes());
        responseDTO.setUserId(trainingSesssaion.getUser().getUserId());
        responseDTO.setTgId(trainingSesssaion.getTrainingGroup().getTgId());
        responseDTO.setName(trainingSesssaion.getName());

        return responseDTO;

    }

    @Transactional
    public TrainingSessionDTO getReportSession(Long sessionId){
        TrainingSession currentSession = trainingSessionRepository.findById(sessionId).orElseThrow();
        TrainingSessionDTO dto = convertDTO(currentSession); // falta adicionar os sets
        List<ExerciseSetDTO> listSet = new ArrayList<>();

        if (currentSession.getExerciseSets() != null) {
            for (ExerciseSet set : currentSession.getExerciseSets()) {
                ExerciseSetDTO sDto = new ExerciseSetDTO();

                sDto.setExerciseId(set.getExercise().getExerciseId());
                sDto.setWeight(set.getWeight());
                sDto.setReps(set.getRepetitions());
                sDto.setSetNumber(set.getSetNumber());
                sDto.setTs_id(currentSession.getSessionId());

                listSet.add(sDto);
            }
        }
        dto.setExerciseSets(listSet);
        return dto;
    }


    @Transactional
    public Page<TrainingSessionDTO> getHistory(Pageable pageable) {
        User user = authService.getAuthenticatedUser();
        Page<TrainingSession> sessions = trainingSessionRepository.findByUserOrderByDateDesc(user, pageable);
        return sessions.map(this::convertDTO);
    }
}
