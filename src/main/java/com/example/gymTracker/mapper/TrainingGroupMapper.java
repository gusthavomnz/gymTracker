package com.example.gymTracker.mapper;

import com.example.gymTracker.dto.request.TrainingGroupRequestDTO;
import com.example.gymTracker.dto.response.TrainingGroupResponseDTO;
import com.example.gymTracker.model.TrainingGroup;
import org.springframework.stereotype.Component;

@Component
public class TrainingGroupMapper {

    public TrainingGroupResponseDTO toDTO(TrainingGroup group) {
        return new TrainingGroupResponseDTO(group.getTgId(), group.getName());
    }

    public TrainingGroup toEntity(TrainingGroupRequestDTO dto) {
        TrainingGroup group = new TrainingGroup();
        group.setName(dto.name());
        return group;
    }
}
