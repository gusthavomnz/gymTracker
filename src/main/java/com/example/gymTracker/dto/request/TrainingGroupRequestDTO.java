package com.example.gymTracker.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TrainingGroupRequestDTO(
        @NotBlank(message = "O nome do grupo muscular é obrigatório")
        String name
) {}
