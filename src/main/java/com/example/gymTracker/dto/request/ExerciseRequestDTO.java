package com.example.gymTracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ExerciseRequestDTO(
        @NotBlank(message = "O nome do exercício é obrigatório")
        String name,

        @NotNull(message = "O grupo muscular é obrigatório")
        Long trainingGroupId
) {}
