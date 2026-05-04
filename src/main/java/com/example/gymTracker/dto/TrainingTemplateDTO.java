package com.example.gymTracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingTemplateDTO {
    private Long templateId;

    @NotBlank(message = "O nome do template é obrigatório")
    private String name;

    @NotNull(message = "A lista de exercícios é obrigatória")
    @NotEmpty(message = "O template deve ter ao menos um exercício")
    private List<Long> exerciseIds;
}
