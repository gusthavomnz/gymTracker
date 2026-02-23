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

@Service
public class TrainingSessionService {

 @Autowired
    ExerciseRepository exerciseRepository;

 @Autowired
 TrainingGroupRepository trainingGroupRepository;

 UserRepository userRepository;


 TrainingSessionRepository trainingSessionRepository;


    @Transactional
    public TrainingSessionDTO createTrainingSession(TrainingSessionDTO trainingSessionDTO){
        // Busca de usuario/Grupo Muscular para garantir integridade referencial:
        User user = userRepository.findById(trainingSessionDTO.getUserId()).orElseThrow();
        TrainingGroup trainingGroup = trainingGroupRepository.findById(trainingSessionDTO.getTgId()).orElseThrow();


        // Inicia o processo de Instanciamento da entidade:
        TrainingSession trainingSession = new TrainingSession();
        trainingSession.setUser(user);
        trainingSession.setTrainingGroup(trainingGroup);
        trainingSession.setDate(trainingSessionDTO.getDate());
        trainingSession.setNotes(trainingSessionDTO.getNotes());

        // Vincula cada série a um exercicio do catalogo(Many-to-one):
        for (ExerciseSetDTO exerciseSetDTO: trainingSessionDTO.getExerciseSets()) {
            ExerciseSet newExerciseSet = new ExerciseSet();
            newExerciseSet.setSetNumber(exerciseSetDTO.getSetNumber());
            newExerciseSet.setWeight(exerciseSetDTO.getWeight());
            newExerciseSet.setRepetitions(exerciseSetDTO.getReps());

            Exercise exercise = exerciseRepository.findById(exerciseSetDTO.getExerciseId())
                    .orElseThrow(() -> new EntityNotFoundException("Exercício ID " + exerciseSetDTO.getExerciseId() + " não encontrado"));


            newExerciseSet.setSetId(exercise.getExerciseId());

            trainingSession.addExerciseSet(newExerciseSet);
        }

        TrainingSession savedSession = trainingSessionRepository.save(trainingSession);


        // Converte para DTO para resposta
        TrainingSessionDTO responseDTO = convertDTO(savedSession);

        return responseDTO;
    }


    public TrainingSessionDTO convertDTO(TrainingSession trainingSesssaion) {
        TrainingSessionDTO responseDTO = new TrainingSessionDTO();

        responseDTO.setTsId(trainingSesssaion.getSessionId());
        responseDTO.setDate(trainingSesssaion.getDate());
        responseDTO.setNotes(trainingSesssaion.getNotes());
        responseDTO.setUserId(trainingSesssaion.getUser().getUserId());
        responseDTO.setTgId(trainingSesssaion.getTrainingGroup().getTgId());

        return responseDTO;

    }
}
