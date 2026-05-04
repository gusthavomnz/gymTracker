package com.example.gymTracker.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TrainingSessionDTO {

    @NotBlank(message = "O nome da sessão é obrigatório")
    private String name;

    private long TsId;

    @NotNull(message = "A data da sessão é obrigatória")
    private LocalDate date;

    private String notes;

    @Positive(message = "O ID do usuário é inválido")
    private long userId;

    @Positive(message = "O ID do grupo de treino é inválido")
    private long tgId;

    @Valid
    private List<ExerciseSetDTO> exerciseSets;

}
