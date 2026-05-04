package com.example.gymTracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExerciseDTO {

    private Long id;

    @NotBlank(message = "O nome do exercício é obrigatório")
    private String name;

    @NotNull(message = "O grupo muscular é obrigatório")
    private Long trainingGroupId;


    public ExerciseDTO(Long id, String name, Long trainingGroupId) {
        this.id = id;
        this.name = name;
        this.trainingGroupId = trainingGroupId;
    }
}

