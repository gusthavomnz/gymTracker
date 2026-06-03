package com.example.gymTracker.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ExerciseSetRequestDTO(
        @NotNull(message = "O ID da sessão de treino é obrigatório")
        Long tsId,

        @NotNull(message = "O exercício é obrigatório")
        Long exerciseId,

        @NotNull(message = "O número de repetições é obrigatório")
        @Min(value = 1, message = "O número de repetições deve ser ao menos 1")
        Integer reps,

        @NotNull(message = "O peso é obrigatório")
        @DecimalMin(value = "0.0", inclusive = true, message = "O peso não pode ser negativo")
        BigDecimal weight
) {}
