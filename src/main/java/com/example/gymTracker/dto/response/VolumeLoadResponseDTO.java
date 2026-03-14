package com.example.gymTracker.dto.response;

import java.math.BigDecimal;
import java.util.Map;

public record VolumeLoadResponseDTO(
    Long sessionId,
    String sessionName,
    BigDecimal totalVolume,
    Map<String, BigDecimal> volumeByExercise
) {}
