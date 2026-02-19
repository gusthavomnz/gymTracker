package service;

import dto.ExerciseDTO;
import dto.ExerciseSetDTO;
import jakarta.persistence.EntityNotFoundException;
import model.Exercise;
import model.TrainingGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.ExerciseRepository;
import repository.TrainingGroupRepository;


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

        return new ExerciseDTO(newExercise.getExercise_id(),
                newExercise.getName(),
                newExercise.getTrainingGroup().getTg_id());
    }

}
