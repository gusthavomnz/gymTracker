package com.example.gymTracker.mapper;

import com.example.gymTracker.dto.request.TrainingSessionRequestDTO;
import com.example.gymTracker.dto.response.ExerciseSetResponseDTO;
import com.example.gymTracker.dto.response.TrainingSessionResponseDTO;
import com.example.gymTracker.model.TrainingGroup;
import com.example.gymTracker.model.TrainingSession;
import com.example.gymTracker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrainingSessionMapper {

    @Autowired
    private ExerciseSetMapper exerciseSetMapper;

    public TrainingSessionResponseDTO toDTO(TrainingSession session) {
        return new TrainingSessionResponseDTO(
                session.getSessionId(),
                session.getName(),
                session.getDate(),
                session.getNotes(),
                session.getUser().getUserId(),
                session.getTrainingGroup().getTgId(),
                Collections.emptyList()
        );
    }

    public TrainingSessionResponseDTO toReportDTO(TrainingSession session) {
        List<ExerciseSetResponseDTO> sets = session.getExerciseSets() == null
                ? Collections.emptyList()
                : session.getExerciseSets().stream()
                        .map(exerciseSetMapper::toDTO)
                        .collect(Collectors.toList());

        return new TrainingSessionResponseDTO(
                session.getSessionId(),
                session.getName(),
                session.getDate(),
                session.getNotes(),
                session.getUser().getUserId(),
                session.getTrainingGroup().getTgId(),
                sets
        );
    }

    public TrainingSession toEntity(TrainingSessionRequestDTO dto, User user, TrainingGroup trainingGroup) {
        TrainingSession session = new TrainingSession();
        session.setName(dto.name());
        session.setUser(user);
        session.setTrainingGroup(trainingGroup);
        session.setDate(dto.date());
        session.setNotes(dto.notes());
        return session;
    }
}
