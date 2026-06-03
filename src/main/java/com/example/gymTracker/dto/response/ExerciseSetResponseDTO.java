package com.example.gymTracker.dto.response;

import java.math.BigDecimal;

public record ExerciseSetResponseDTO(
        Long tsId,
        Long exerciseId,
        Integer setNumber,
        Integer reps,
        BigDecimal weight
) {}
