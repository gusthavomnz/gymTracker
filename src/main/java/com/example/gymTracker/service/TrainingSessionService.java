package com.example.gymTracker.service;


import com.example.gymTracker.dto.ExerciseSetDTO;
import com.example.gymTracker.dto.TrainingSessionDTO;
import com.example.gymTracker.model.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.gymTracker.repository.ExerciseRepository;
import com.example.gymTracker.repository.TrainingGroupRepository;
import com.example.gymTracker.repository.TrainingSessionRepository;
import com.example.gymTracker.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainingSessionService {

 @Autowired
    ExerciseRepository exerciseRepository;

 @Autowired
 TrainingGroupRepository trainingGroupRepository;

    @Autowired
 UserRepository userRepository;

@Autowired
 TrainingSessionRepository trainingSessionRepository;


    @Transactional
    public TrainingSessionDTO createTrainingSession(TrainingSessionDTO trainingSessionDTO){
        // Busca de usuario/Grupo Muscular para garantir integridade referencial:
        User user = userRepository.findById(trainingSessionDTO.getUserId()).orElseThrow();
        TrainingGroup trainingGroup = trainingGroupRepository.findById(trainingSessionDTO.getTgId()).orElseThrow();


        // Inicia o processo de Instanciamento da entidade:
        TrainingSession trainingSession = new TrainingSession();
        trainingSession.setName(trainingSessionDTO.getName());
        trainingSession.setUser(user);
        trainingSession.setTrainingGroup(trainingGroup);
        trainingSession.setDate(trainingSessionDTO.getDate());
        trainingSession.setNotes(trainingSessionDTO.getNotes());

        TrainingSession savedSession = trainingSessionRepository.save(trainingSession);

        // Converte para DTO para resposta
        TrainingSessionDTO responseDTO = convertDTO(savedSession);

        return responseDTO;
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
}
