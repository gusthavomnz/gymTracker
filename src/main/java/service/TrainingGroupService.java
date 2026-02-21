package service;


import dto.TrainingGroupDTO;
import model.TrainingGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.TrainingGroupRepository;

@Service
public class TrainingGroupService {

    @Autowired
    TrainingGroupRepository trainingGroupRepository;


public TrainingGroupDTO createTrainingGroup(TrainingGroupDTO trainingGroupDTO){

    // CONVERTE DTO PARA ENTITY
    TrainingGroup newTrainingGroup = new TrainingGroup();
    newTrainingGroup.setName(trainingGroupDTO.getName());

    // SALVA NO BD E CAPTURA
    TrainingGroup savedGroup = trainingGroupRepository.save(newTrainingGroup);

    // CONVERTE A CAPTURA EM DTO PARA RETORNAR
    TrainingGroupDTO responseDTO = new TrainingGroupDTO(
            savedGroup.getTgId(),
            savedGroup.getName());

    return responseDTO;
}






}
