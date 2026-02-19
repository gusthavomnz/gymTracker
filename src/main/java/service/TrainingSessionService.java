package service;


import dto.ExerciseDTO;
import dto.ExerciseSetDTO;
import dto.TrainingSessionDTO;
import dto.UserRegisterDTO;
import jakarta.persistence.EntityNotFoundException;
import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.ExerciseRepository;
import repository.TrainingGroupRepository;
import repository.TrainingSessionRepository;
import repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class TrainingSessionService {

 @Autowired
    ExerciseRepository exerciseRepository;

 @Autowired
 TrainingGroupRepository trainingGroupRepository;

 UserRepository userRepository;


 TrainingSessionRepository trainingSessionRepository;
/*
    private LocalDate date;
    private String notes;
    private long userId;
    private long tgId;
    private List<ExerciseSetDTO> exerciseSets;
*/


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
            newExerciseSet.setNumberSets(exerciseSetDTO.getSetNumber());
            newExerciseSet.setWeight(exerciseSetDTO.getWeight());
            newExerciseSet.setReps(exerciseSetDTO.getReps());

            Exercise exercise = exerciseRepository.findById(exerciseSetDTO.getExerciseId())
                    .orElseThrow(() -> new EntityNotFoundException("Exercício ID " + exerciseSetDTO.getExerciseId() + " não encontrado"));


            newExerciseSet.setEs_id(exercise.getExercise_id());

            trainingSession.addExerciseSet(newExerciseSet);
        }

        TrainingSession savedSession = trainingSessionRepository.save(trainingSession);


        // Converte para DTO para resposta
        TrainingSessionDTO responseDTO = convertDTO(savedSession);

        return responseDTO;
    }


    public TrainingSessionDTO convertDTO(TrainingSession trainingSesssaion) {
        TrainingSessionDTO responseDTO = new TrainingSessionDTO();

        responseDTO.setTsId(trainingSesssaion.getTs_id());
        responseDTO.setDate(trainingSesssaion.getDate());
        responseDTO.setNotes(trainingSesssaion.getNotes());
        responseDTO.setUserId(trainingSesssaion.getUser().getUser_id());
        responseDTO.setTgId(trainingSesssaion.getTrainingGroup().getTg_id());

        return responseDTO;

    }
}
