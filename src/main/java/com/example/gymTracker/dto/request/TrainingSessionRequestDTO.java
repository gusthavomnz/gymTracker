package com.example.gymTracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record TrainingSessionRequestDTO(
        @NotBlank(message = "O nome da sessão é obrigatório")
        String name,

        @NotNull(message = "A data da sessão é obrigatória")
        LocalDate date,

        String notes,

        @Positive(message = "O ID do grupo de treino é inválido")
        long tgId
) {}
