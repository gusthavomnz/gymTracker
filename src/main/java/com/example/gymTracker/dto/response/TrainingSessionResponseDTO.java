package com.example.gymTracker.dto.response;

import java.time.LocalDate;
import java.util.List;

public record TrainingSessionResponseDTO(
        long tsId,
        String name,
        LocalDate date,
        String notes,
        long userId,
        long tgId,
        List<ExerciseSetResponseDTO> exerciseSets
) {}
