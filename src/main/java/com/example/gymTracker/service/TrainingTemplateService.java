package com.example.gymTracker.service;

import com.example.gymTracker.config.AuthService;
import com.example.gymTracker.dto.TrainingTemplateDTO;
import com.example.gymTracker.model.Exercise;
import com.example.gymTracker.model.TrainingTemplate;
import com.example.gymTracker.model.User;
import com.example.gymTracker.repository.ExerciseRepository;
import com.example.gymTracker.repository.TrainingTemplateRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainingTemplateService {

    @Autowired
    private TrainingTemplateRepository trainingTemplateRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private AuthService authService;

    @Transactional
    public TrainingTemplateDTO createTemplate(TrainingTemplateDTO dto) {
        User user = authService.getAuthenticatedUser();
        List<Exercise> exercises = exerciseRepository.findAllById(dto.getExerciseIds());

        TrainingTemplate template = new TrainingTemplate();
        template.setName(dto.getName());
        template.setUser(user);
        template.setExercises(exercises);

        TrainingTemplate saved = trainingTemplateRepository.save(template);
        return mapToDTO(saved);
    }

    public List<TrainingTemplateDTO> listMyTemplates() {
        User user = authService.getAuthenticatedUser();
        return trainingTemplateRepository.findByUser(user).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteTemplate(Long id) {
        User user = authService.getAuthenticatedUser();
        TrainingTemplate template = trainingTemplateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Template not found"));
        
        if (!template.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Unauthorized");
        }
        
        trainingTemplateRepository.delete(template);
    }

    private TrainingTemplateDTO mapToDTO(TrainingTemplate template) {
        return new TrainingTemplateDTO(
                template.getTemplateId(),
                template.getName(),
                template.getExercises().stream().map(Exercise::getExerciseId).collect(Collectors.toList())
        );
    }
}
