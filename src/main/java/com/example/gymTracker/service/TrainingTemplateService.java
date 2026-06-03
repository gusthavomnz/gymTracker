package com.example.gymTracker.service;

import com.example.gymTracker.config.AuthService;
import com.example.gymTracker.dto.request.TrainingTemplateRequestDTO;
import com.example.gymTracker.dto.response.TrainingTemplateResponseDTO;
import com.example.gymTracker.mapper.TrainingTemplateMapper;
import com.example.gymTracker.model.Exercise;
import com.example.gymTracker.model.TrainingTemplate;
import com.example.gymTracker.model.User;
import com.example.gymTracker.repository.TrainingTemplateRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainingTemplateService {

    @Autowired
    private TrainingTemplateRepository trainingTemplateRepository;

    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private AuthService authService;

    @Autowired
    private TrainingTemplateMapper trainingTemplateMapper;

    @Transactional
    public TrainingTemplateResponseDTO createTemplate(TrainingTemplateRequestDTO dto) {
        User user = authService.getAuthenticatedUser();
        List<Exercise> exercises = exerciseService.findAllEntitiesByIds(dto.exerciseIds());
        TrainingTemplate entity = trainingTemplateMapper.toEntity(dto, user, exercises);
        TrainingTemplate saved = trainingTemplateRepository.save(entity);
        return trainingTemplateMapper.toDTO(saved);
    }

    public List<TrainingTemplateResponseDTO> listMyTemplates() {
        User user = authService.getAuthenticatedUser();
        return trainingTemplateRepository.findByUserWithExercises(user).stream()
                .map(trainingTemplateMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteTemplate(Long id) {
        User user = authService.getAuthenticatedUser();
        TrainingTemplate template = trainingTemplateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Template not found"));

        if (!template.getUser().getUserId().equals(user.getUserId())) {
            throw new AccessDeniedException("You do not have permission to delete this template");
        }

        trainingTemplateRepository.delete(template);
    }
}
