package com.example.gymTracker.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PersonalRecordDTO(
    String exerciseName,
    BigDecimal weight,
    Integer repetitions,
    LocalDate date,
    BigDecimal estimatedOneRepMax
) {}
