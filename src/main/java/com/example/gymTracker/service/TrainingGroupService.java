package com.example.gymTracker.service;

import com.example.gymTracker.dto.request.TrainingGroupRequestDTO;
import com.example.gymTracker.dto.response.TrainingGroupResponseDTO;
import com.example.gymTracker.mapper.TrainingGroupMapper;
import com.example.gymTracker.model.TrainingGroup;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.gymTracker.repository.TrainingGroupRepository;

@Service
public class TrainingGroupService {

    @Autowired
    private TrainingGroupRepository trainingGroupRepository;

    @Autowired
    private TrainingGroupMapper trainingGroupMapper;

    public TrainingGroupResponseDTO createTrainingGroup(TrainingGroupRequestDTO dto) {
        TrainingGroup entity = trainingGroupMapper.toEntity(dto);
        TrainingGroup saved = trainingGroupRepository.save(entity);
        return trainingGroupMapper.toDTO(saved);
    }

    public TrainingGroup findEntityById(Long id) {
        return trainingGroupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Training Group not found"));
    }
}
