package com.example.gymTracker.service;

import com.example.gymTracker.dto.request.TrainingSessionRequestDTO;
import com.example.gymTracker.dto.response.TrainingSessionResponseDTO;
import com.example.gymTracker.mapper.TrainingSessionMapper;
import com.example.gymTracker.model.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.gymTracker.config.AuthService;
import com.example.gymTracker.repository.TrainingSessionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class TrainingSessionService {

    @Autowired
    private TrainingSessionRepository trainingSessionRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private TrainingGroupService trainingGroupService;

    @Autowired
    private TrainingSessionMapper trainingSessionMapper;

    @Transactional
    public TrainingSessionResponseDTO createTrainingSession(TrainingSessionRequestDTO dto) {
        User user = authService.getAuthenticatedUser();
        TrainingGroup trainingGroup = trainingGroupService.findEntityById(dto.tgId());
        TrainingSession entity = trainingSessionMapper.toEntity(dto, user, trainingGroup);
        TrainingSession saved = trainingSessionRepository.save(entity);
        return trainingSessionMapper.toDTO(saved);
    }

    @Transactional(readOnly = true)
    public TrainingSessionResponseDTO getReportSession(Long sessionId) {
        TrainingSession session = findFullSession(sessionId);
        return trainingSessionMapper.toReportDTO(session);
    }

    @Transactional(readOnly = true)
    public Page<TrainingSessionResponseDTO> getHistory(Pageable pageable) {
        User user = authService.getAuthenticatedUser();
        return trainingSessionRepository.findByUserWithDetailsOrderByDateDesc(user, pageable)
                .map(trainingSessionMapper::toDTO);
    }

    public TrainingSession findEntityById(Long id) {
        return trainingSessionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Training session not found"));
    }

    public TrainingSession findFullSession(Long id) {
        return trainingSessionRepository.findFullSession(id)
                .orElseThrow(() -> new EntityNotFoundException("Training session not found"));
    }
}
