package com.example.gymTracker.mapper;

import com.example.gymTracker.dto.request.TrainingTemplateRequestDTO;
import com.example.gymTracker.dto.response.TrainingTemplateResponseDTO;
import com.example.gymTracker.model.Exercise;
import com.example.gymTracker.model.TrainingTemplate;
import com.example.gymTracker.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrainingTemplateMapper {

    public TrainingTemplateResponseDTO toDTO(TrainingTemplate template) {
        return new TrainingTemplateResponseDTO(
                template.getTemplateId(),
                template.getName(),
                template.getExercises().stream()
                        .map(Exercise::getExerciseId)
                        .collect(Collectors.toList())
        );
    }

    public TrainingTemplate toEntity(TrainingTemplateRequestDTO dto, User user, List<Exercise> exercises) {
        TrainingTemplate template = new TrainingTemplate();
        template.setName(dto.name());
        template.setUser(user);
        template.setExercises(exercises);
        return template;
    }
}
