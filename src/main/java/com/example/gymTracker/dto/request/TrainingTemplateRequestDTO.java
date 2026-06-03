package com.example.gymTracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record TrainingTemplateRequestDTO(
        @NotBlank(message = "O nome do template é obrigatório")
        String name,

        @NotNull(message = "A lista de exercícios é obrigatória")
        @NotEmpty(message = "O template deve ter ao menos um exercício")
        List<Long> exerciseIds
) {}
